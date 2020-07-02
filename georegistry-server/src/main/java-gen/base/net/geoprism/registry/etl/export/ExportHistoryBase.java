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

@com.runwaysdk.business.ClassSignature(hash = 961615369)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to ExportHistory.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class ExportHistoryBase extends com.runwaysdk.system.scheduler.JobHistory
{
  public final static String CLASS = "net.geoprism.registry.etl.export.ExportHistory";
  public static java.lang.String EXPORTEDRECORDS = "exportedRecords";
  public static java.lang.String STAGE = "stage";
  private static final long serialVersionUID = 961615369;
  
  public ExportHistoryBase()
  {
    super();
  }
  
  public Long getExportedRecords()
  {
    return com.runwaysdk.constants.MdAttributeLongUtil.getTypeSafeValue(getValue(EXPORTEDRECORDS));
  }
  
  public void validateExportedRecords()
  {
    this.validateAttribute(EXPORTEDRECORDS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeLongDAOIF getExportedRecordsMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.etl.export.ExportHistory.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeLongDAOIF)mdClassIF.definesAttribute(EXPORTEDRECORDS);
  }
  
  public void setExportedRecords(Long value)
  {
    if(value == null)
    {
      setValue(EXPORTEDRECORDS, "");
    }
    else
    {
      setValue(EXPORTEDRECORDS, java.lang.Long.toString(value));
    }
  }
  
  @SuppressWarnings("unchecked")
  public java.util.List<net.geoprism.registry.etl.export.ExportStage> getStage()
  {
    return (java.util.List<net.geoprism.registry.etl.export.ExportStage>) getEnumValues(STAGE);
  }
  
  public void addStage(net.geoprism.registry.etl.export.ExportStage value)
  {
    if(value != null)
    {
      addEnumItem(STAGE, value.getOid());
    }
  }
  
  public void removeStage(net.geoprism.registry.etl.export.ExportStage value)
  {
    if(value != null)
    {
      removeEnumItem(STAGE, value.getOid());
    }
  }
  
  public void clearStage()
  {
    clearEnum(STAGE);
  }
  
  public void validateStage()
  {
    this.validateAttribute(STAGE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeEnumerationDAOIF getStageMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.etl.export.ExportHistory.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeEnumerationDAOIF)mdClassIF.definesAttribute(STAGE);
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public static ExportHistoryQuery getAllInstances(String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    ExportHistoryQuery query = new ExportHistoryQuery(new com.runwaysdk.query.QueryFactory());
    com.runwaysdk.business.Entity.getAllInstances(query, sortAttribute, ascending, pageSize, pageNumber);
    return query;
  }
  
  public static ExportHistory get(String oid)
  {
    return (ExportHistory) com.runwaysdk.business.Business.get(oid);
  }
  
  public static ExportHistory getByKey(String key)
  {
    return (ExportHistory) com.runwaysdk.business.Business.get(CLASS, key);
  }
  
  public static ExportHistory lock(java.lang.String oid)
  {
    ExportHistory _instance = ExportHistory.get(oid);
    _instance.lock();
    
    return _instance;
  }
  
  public static ExportHistory unlock(java.lang.String oid)
  {
    ExportHistory _instance = ExportHistory.get(oid);
    _instance.unlock();
    
    return _instance;
  }
  
  public String toString()
  {
    if (this.isNew())
    {
      return "New: "+ this.getClassDisplayLabel();
    }
    else
    {
      return super.toString();
    }
  }
}
