/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Geoprism Registry(tm).
 *
 * Geoprism Registry(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Geoprism Registry(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Geoprism Registry(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package net.geoprism.registry.etl.export;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runwaysdk.LocalizationFacade;
import com.runwaysdk.RunwayException;
import com.runwaysdk.business.graph.GraphQuery;
import com.runwaysdk.business.graph.VertexObject;
import com.runwaysdk.dataaccess.MdAttributeDAOIF;
import com.runwaysdk.dataaccess.MdVertexDAOIF;
import com.runwaysdk.dataaccess.graph.attributes.ValueOverTime;
import com.runwaysdk.dataaccess.metadata.MdAttributeDAO;
import com.runwaysdk.dataaccess.transaction.Transaction;
import com.runwaysdk.query.OIterator;
import com.runwaysdk.query.QueryFactory;
import com.runwaysdk.system.scheduler.AllJobStatus;
import com.runwaysdk.system.scheduler.ExecutionContext;
import com.runwaysdk.system.scheduler.JobHistory;
import com.runwaysdk.system.scheduler.JobHistoryRecord;
import com.runwaysdk.system.scheduler.QuartzRunwayJob;
import com.runwaysdk.system.scheduler.QueueingQuartzJob;

import net.geoprism.dhis2.dhis2adapter.DHIS2Facade;
import net.geoprism.dhis2.dhis2adapter.HTTPConnector;
import net.geoprism.dhis2.dhis2adapter.exception.HTTPException;
import net.geoprism.dhis2.dhis2adapter.exception.InvalidLoginException;
import net.geoprism.dhis2.dhis2adapter.response.DHIS2ImportResponse;
import net.geoprism.dhis2.dhis2adapter.response.DHIS2Response;
import net.geoprism.dhis2.dhis2adapter.response.model.ErrorReport;
import net.geoprism.registry.SynchronizationConfig;
import net.geoprism.registry.etl.DHIS2SyncConfig;
import net.geoprism.registry.etl.ExportJobHasErrors;
import net.geoprism.registry.etl.NewGeoObjectInvalidSyncTypeError;
import net.geoprism.registry.etl.RemoteConnectionException;
import net.geoprism.registry.etl.SyncLevel;
import net.geoprism.registry.graph.DHIS2ExternalSystem;
import net.geoprism.registry.graph.GeoVertex;
import net.geoprism.registry.model.ServerGeoObjectType;
import net.geoprism.registry.model.graph.VertexServerGeoObject;

/**
 * This class is currently hardcoded to DHIS2 export, however the metadata is attempting to be generic enough
 * to scale to more generic usecases.
 * 
 * @author rrowlands
 * @author jsmethie
 *
 */
public class DataExportJob extends DataExportJobBase
{
  private static final long     serialVersionUID = -1821569567;

  private static final Logger   logger           = LoggerFactory.getLogger(DataExportJob.class);

  private SynchronizationConfig syncConfig;

  private DHIS2SyncConfig       dhis2Config;

  private DHIS2Facade           dhis2;
  
  private ExportHistory         history;

  private JobExportError exportWarning;

  public DataExportJob()
  {
    super();
  }

  private class JobExportError extends RunwayException
  {
    private static final long serialVersionUID = 8463740942015611693L;

    protected DHIS2ImportResponse    response;
    
    protected String          submittedJson;

    protected Throwable       error;
    
    protected String          geoObjectCode;
    
    protected Long            rowIndex;

    private JobExportError(Long rowIndex, DHIS2ImportResponse response, String submittedJson, Throwable t, String geoObjectCode)
    {
      super("");
      this.response = response;
      this.submittedJson = submittedJson;
      this.error = t;
      this.geoObjectCode = geoObjectCode;
      this.rowIndex = rowIndex;
    }
  }

  @Override
  public synchronized JobHistory start()
  {
    throw new UnsupportedOperationException();
  }

  public synchronized ExportHistory start(SynchronizationConfig configuration)
  {
    return executableJobStart(configuration);
  }

  private ExportHistory executableJobStart(SynchronizationConfig configuration)
  {
    JobHistoryRecord record = startInTrans(configuration);

    this.getQuartzJob().start(record);

    return (ExportHistory) record.getChild();
  }

  @Transaction
  private JobHistoryRecord startInTrans(SynchronizationConfig configuration)
  {
    ExportHistory history = (ExportHistory) this.createNewHistory();

    // TODO
    // configuration.setHistoryId(history.getOid());
    // configuration.setJobId(this.getOid());

    // history.appLock();
    // history.setConfig(configuration);
    // history.apply();

    JobHistoryRecord record = new JobHistoryRecord(this, history);
    record.apply();

    return record;
  }

  /**
   * We want the actual API version, which is different than the DHIS2 core
   * version. This function will consume the DHIS2 version and return an API
   * version.
   * 
   * @return
   */
  private String getAPIVersion()
  {
    String in = this.dhis2Config.getSystem().getVersion();

    if (in.startsWith("2.31"))
    {
      return "26";
    }

    return "26"; // We currently only support API version 26 right now anyway
  }

  @Override
  public void execute(ExecutionContext executionContext) throws Throwable
  {
    this.history = (ExportHistory) executionContext.getJobHistoryRecord().getChild();
    
    this.syncConfig = this.getConfig();
    this.dhis2Config = (DHIS2SyncConfig) this.getConfig().buildConfiguration();

    DHIS2ExternalSystem system = this.dhis2Config.getSystem();

    HTTPConnector connector = new HTTPConnector();
    connector.setServerUrl(system.getUrl());
    connector.setCredentials(system.getUsername(), system.getPassword());

    dhis2 = new DHIS2Facade(connector, this.getAPIVersion());

    this.setStage(history, ExportStage.EXPORT);

    this.doExport();
  }

  private void setStage(ExportHistory history, ExportStage stage)
  {
    history.appLock();
    history.clearStage();
    history.addStage(stage);
    history.apply();
  }

  private long getCount(ServerGeoObjectType got)
  {
    MdVertexDAOIF mdVertex = got.getMdVertex();

    StringBuilder statement = new StringBuilder();
    statement.append("SELECT COUNT(*) FROM " + mdVertex.getDBClassName());

    return new GraphQuery<Long>(statement.toString()).getSingleResult();
  }

  public List<VertexServerGeoObject> query(ServerGeoObjectType got, long skip, long pageSize)
  {
    MdVertexDAOIF mdVertex = got.getMdVertex();
    MdAttributeDAOIF mdAttribute = MdAttributeDAO.getByKey(GeoVertex.CLASS + "." + GeoVertex.LASTUPDATEDATE);

    StringBuilder statement = new StringBuilder();
    statement.append("SELECT FROM " + mdVertex.getDBClassName());
    statement.append(" ORDER BY " + mdAttribute.getColumnName() + ", oid ASC");
    statement.append(" SKIP " + skip + " LIMIT " + pageSize);

    GraphQuery<VertexObject> query = new GraphQuery<VertexObject>(statement.toString());

    List<VertexObject> vObjects = query.getResults();

    List<VertexServerGeoObject> response = new LinkedList<VertexServerGeoObject>();

    for (VertexObject vObject : vObjects)
    {
      VertexServerGeoObject vSGO = new VertexServerGeoObject(got, vObject);
      vSGO.setDate(ValueOverTime.INFINITY_END_DATE);

      response.add(vSGO);
    }

    return response;
  }

  private void doExport()
  {
    long rowIndex = 0;
    long total = 0;
    long exportCount = 0;
    
    List<SyncLevel> levels = this.dhis2Config.getLevels();
    
    Boolean includeTranslations = LocalizationFacade.getInstalledLocales().size() > 0;

    for (SyncLevel level : levels)
    {
      long skip = 0;
      long pageSize = 1000;

      long count = this.getCount(level.getGeoObjectType());
      total += count;

      while (skip < count)
      {
        List<VertexServerGeoObject> objects = this.query(level.getGeoObjectType(), skip, pageSize);

        for (VertexServerGeoObject go : objects) {
          try
          {
            this.exportWarning = null;
            
            exportGeoObject(level, rowIndex, go, includeTranslations);
            
            exportCount++;
            
            this.history.appLock();
            this.history.setWorkProgress(rowIndex);
            this.history.setExportedRecords(exportCount);
            this.history.apply();
          }
          catch (JobExportError ee)
          {
            recordExportError(ee);
          }
          
          if (this.exportWarning != null)
          {
            recordExportError(this.exportWarning);
          }
          
          rowIndex++;
        };

        skip += pageSize;
      }
    }
    
    this.history.appLock();
    this.history.setWorkTotal(total);
    this.history.setWorkProgress(rowIndex);
    this.history.setExportedRecords(exportCount);
    history.clearStage();
    history.addStage(ExportStage.COMPLETE);
    this.history.apply();
    
    ExportErrorQuery query = new ExportErrorQuery(new QueryFactory());
    query.WHERE(query.getHistory().EQ(this.history));
    Boolean hasErrors = query.getCount() > 0;
    
    if (hasErrors)
    {
      ExportJobHasErrors ex = new ExportJobHasErrors();
      
      throw ex;
    }
  }

  private void recordExportError(JobExportError ee)
  {
    DHIS2ImportResponse resp = ee.response;
    Throwable ex = ee.error;
    String geoObjectCode = ee.geoObjectCode;
    
    ExportError exportError = new ExportError();

    if (ee.submittedJson != null)
    {
      exportError.setSubmittedJson(ee.submittedJson);
    }
    
    if (resp != null)
    {
      if (resp.getResponse() != null && resp.getResponse().length() > 0)
      {
        exportError.setResponseJson(resp.getResponse());
        
        if (resp.hasErrorReports())
        {
          List<ErrorReport> reports = resp.getErrorReports();
          
          ErrorReport report = reports.get(0);
          
          exportError.setErrorMessage(report.getMessage());
        }
      }
      
      exportError.setErrorCode(resp.getStatusCode());
    }
    
    exportError.setCode(geoObjectCode);
    
    if (ex != null)
    {
      exportError.setErrorJson(JobHistory.exceptionToJson(ex).toString());
    }
    
    exportError.setRowIndex(ee.rowIndex);
    
    exportError.setHistory(this.history);
    
    exportError.apply();
  }

  /**
   * It's important that we try our best to maintain an accurate state between
   * our database and the DHIS2 database. The DHIS2Serailizer will create new
   * ids and save them as externalIds for GeoObjects that do not have
   * externalIds. If our push to DHIS2 for this new GeoObject fails for whatever
   * reason, then we want to rollback our database so that we do not store the
   * id which does not exist in the DHIS2 database.
   * 
   * TODO : In the future perhaps we should directly ask DHIS2 if an object
   * exists and then we'll know
   * 
   * @param level
   * @param go
   * @return
   * @throws ExportError
   */
  @Transaction
  private void exportGeoObject(SyncLevel level, Long rowIndex, VertexServerGeoObject serverGo, Boolean includeTranslations) throws JobExportError
  {
    DHIS2ImportResponse resp = null;
    
    JsonObject orgUnitJsonTree = null;
    String orgUnitJson = null;
    
    String externalId = null;
    
    try
    {
      externalId = serverGo.getExternalId(this.syncConfig.getExternalSystem());
      boolean isNew = (externalId == null);

      if (isNew && level.getSyncType() != SyncLevel.Type.ALL)
      {
        NewGeoObjectInvalidSyncTypeError err = new NewGeoObjectInvalidSyncTypeError();
        err.setGeoObject(serverGo.getDisplayLabel().getValue());
        throw err;
      }

      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(VertexServerGeoObject.class, new DHIS2GeoObjectJsonAdapters.DHIS2Serializer(this.dhis2, level, level.getGeoObjectType(), this.syncConfig.getServerHierarchyType(), this.syncConfig.getExternalSystem()));
      
      orgUnitJsonTree = builder.create().toJsonTree(serverGo, serverGo.getClass()).getAsJsonObject();
      orgUnitJson = orgUnitJsonTree.toString();
      
      externalId = serverGo.getExternalId(this.syncConfig.getExternalSystem());

      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair("mergeMode", "MERGE"));

      try
      {
        JsonObject metadataPayload = new JsonObject();
        JsonArray orgUnits = new JsonArray();
        metadataPayload.add("organisationUnits", orgUnits);
        
        if (level.getSyncType() == SyncLevel.Type.ALL)
        {
          orgUnits.add(orgUnitJsonTree);
        }
        else if (level.getSyncType() == SyncLevel.Type.RELATIONSHIPS)
        {
          if (!orgUnitJsonTree.has("parent"))
          {
            return; // Root level types do not have parents.
          }
          
          JsonObject orgUnitRelationships = new JsonObject();
          
          
          // These attributes must be included at the requirement of DHIS2 API
          orgUnitRelationships.addProperty("id", orgUnitJsonTree.get("id").getAsString());
          orgUnitRelationships.addProperty("name", orgUnitJsonTree.get("name").getAsString());
          orgUnitRelationships.addProperty("shortName", orgUnitJsonTree.get("shortName").getAsString());
          orgUnitRelationships.addProperty("openingDate", orgUnitJsonTree.get("openingDate").getAsString());
          
          // We don't want to actually update any of these attributes, so if we can fetch the object from DHIS2
          // then use the values they have in their server instead.
          DHIS2Response orgUnitGetResp = dhis2.entityIdGet("organisationUnits", externalId, params);
          if (orgUnitGetResp.isSuccess())
          {
            JsonObject jo = JsonParser.parseString(orgUnitGetResp.getResponse()).getAsJsonObject();
            
            orgUnitRelationships.addProperty("name", jo.get("name").getAsString());
            orgUnitRelationships.addProperty("shortName", jo.get("shortName").getAsString());
            orgUnitRelationships.addProperty("openingDate", jo.get("openingDate").getAsString());
          }
          
          // These attributes are the ones we need to include to change the relationship
          orgUnitRelationships.add("parent", orgUnitJsonTree.get("parent").getAsJsonObject());
          orgUnitRelationships.addProperty("path", orgUnitJsonTree.get("path").getAsString());
          orgUnitRelationships.addProperty("level", orgUnitJsonTree.get("level").getAsInt());
          
          orgUnits.add(orgUnitRelationships);
        }
        else if (level.getSyncType() == SyncLevel.Type.ORG_UNITS)
        {
          JsonObject orgUnitAttributes = orgUnitJsonTree.deepCopy();
          
          // Drop all attributes related to the parent / hierarchy
          orgUnitAttributes.remove("parent");
          orgUnitAttributes.remove("path");
          orgUnitAttributes.remove("level");
          
          orgUnits.add(orgUnitAttributes);
        }
        
        resp = dhis2.metadataPost(params, new StringEntity(metadataPayload.toString(), Charset.forName("UTF-8")));
      }
      catch (InvalidLoginException e)
      {
        LoginException cgrlogin = new LoginException(e);
        throw cgrlogin;
      }
      catch (HTTPException e)
      {
        HttpError cgrhttp = new HttpError(e);
        throw cgrhttp;
      }

      if (!resp.isSuccess())
      {
        if (resp.hasMessage())
        {
          ExportRemoteException ere = new ExportRemoteException();
          ere.setRemoteError(resp.getMessage());
          throw ere;
        }
        else
        {
          UnexpectedRemoteResponse re = new UnexpectedRemoteResponse();
          throw re;
        }
      }
    }
    catch (Throwable t)
    {
      JobExportError er = new JobExportError(rowIndex, resp, orgUnitJson, t, serverGo.getCode());
      throw er;
    }
  }

  @Override
  protected JobHistory createNewHistory()
  {
    ExportHistory history = new ExportHistory();
    history.setStartTime(new Date());
    history.addStatus(AllJobStatus.NEW);
    history.addStage(ExportStage.CONNECTING);
    history.apply();

    return history;
  }

  public boolean canResume()
  {
    return false;
  }

  @Override
  protected QuartzRunwayJob createQuartzRunwayJob()
  {
    return new QueueingQuartzJob(this);
  }

  public static List<? extends DataExportJob> getAll(SynchronizationConfig config)
  {
    DataExportJobQuery query = new DataExportJobQuery(new QueryFactory());
    query.WHERE(query.getConfig().EQ(config));

    try (OIterator<? extends DataExportJob> it = query.getIterator())
    {
      return it.getAll();
    }
  }
}
