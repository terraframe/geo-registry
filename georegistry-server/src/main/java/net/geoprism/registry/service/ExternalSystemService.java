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
package net.geoprism.registry.service;

import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runwaysdk.dataaccess.transaction.Transaction;
import com.runwaysdk.json.RunwayJsonAdapters;
import com.runwaysdk.session.Request;
import com.runwaysdk.session.RequestType;

import net.geoprism.account.OauthServer;
import net.geoprism.registry.Organization;
import net.geoprism.registry.conversion.LocalizedValueConverter;
import net.geoprism.registry.dhis2.DHIS2FeatureService;
import net.geoprism.registry.graph.DHIS2ExternalSystem;
import net.geoprism.registry.graph.ExternalSystem;
import net.geoprism.registry.view.Page;

public class ExternalSystemService
{
  @Request(RequestType.SESSION)
  public JsonObject page(String sessionId, Integer pageNumber, Integer pageSize) throws JSONException
  {
    long count = ExternalSystem.getCount();
    List<ExternalSystem> results = ExternalSystem.getExternalSystemsForOrg(pageNumber, pageSize);

    return new Page<ExternalSystem>(count, pageNumber, pageSize, results).toJSON();
  }

  @Request(RequestType.SESSION)
  public JsonObject apply(String sessionId, String json) throws JSONException
  {
    return applyInTrans(json);
  }
  
  @Transaction
  private JsonObject applyInTrans(String json)
  {
    JsonObject jo = JsonParser.parseString(json).getAsJsonObject();

    ExternalSystem system = ExternalSystem.desieralize(jo);
    system.apply();
    
    if (system instanceof DHIS2ExternalSystem)
    {
      DHIS2ExternalSystem dhis2Sys = (DHIS2ExternalSystem) system;
      
      if (jo.has(DHIS2ExternalSystem.OAUTH_SERVER) && !jo.get(DHIS2ExternalSystem.OAUTH_SERVER).isJsonNull())
      {
        Gson gson2 = new GsonBuilder().registerTypeAdapter(OauthServer.class, new RunwayJsonAdapters.RunwayDeserializer()).create();
        OauthServer oauth = gson2.fromJson(jo.get(DHIS2ExternalSystem.OAUTH_SERVER), OauthServer.class);
        
        OauthServer dbServer = dhis2Sys.getOauthServer();
        if (dbServer != null)
        {
          dbServer.lock();
          dbServer.populate(oauth);
          
          oauth = dbServer;
        }
        
        String systemLabel = LocalizedValueConverter.convert(system.getEmbeddedComponent(ExternalSystem.LABEL)).getValue();
        oauth.getDisplayLabel().setValue(systemLabel);
        
        oauth.apply();
        
        dhis2Sys.setOauthServer(oauth);
        dhis2Sys.apply();
      }
      else if (dhis2Sys.getOauthServer() != null)
      {
        OauthServer existingOauth = dhis2Sys.getOauthServer();
        
        dhis2Sys.setOauthServerId(null);
        dhis2Sys.apply();
        
        existingOauth.delete();
      }
      
      // Test the DHIS2 connection information
      new DHIS2FeatureService().setExternalSystemDhis2Version(dhis2Sys);
    }

    return system.toJSON();
  }

  @Request(RequestType.SESSION)
  public JsonObject get(String sessionId, String oid) throws JSONException
  {
    ExternalSystem system = ExternalSystem.get(oid);

    return system.toJSON();
  }

  @Request(RequestType.SESSION)
  public void remove(String sessionId, String oid)
  {
    ExternalSystem system = ExternalSystem.get(oid);
    Organization organization = system.getOrganization();

    ServiceFactory.getRolePermissionService().enforceRA(organization.getCode());

    if (system instanceof DHIS2ExternalSystem)
    {
      DHIS2ExternalSystem dhis2Sys = (DHIS2ExternalSystem) system;
      
      if (dhis2Sys.getOauthServer() != null)
      {
        OauthServer dbServer = dhis2Sys.getOauthServer();
        
        dbServer.delete();
      }
    }
    
    system.delete();
  }
}
