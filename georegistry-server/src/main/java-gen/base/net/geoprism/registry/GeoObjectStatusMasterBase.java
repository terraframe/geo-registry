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
package net.geoprism.registry;

@com.runwaysdk.business.ClassSignature(hash = 1933609193)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to GeoObjectStatusMaster.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class GeoObjectStatusMasterBase extends com.runwaysdk.system.EnumerationMaster
{
  public final static String CLASS = "net.geoprism.registry.GeoObjectStatusMaster";
  public static java.lang.String STATUSORDER = "statusOrder";
  private static final long serialVersionUID = 1933609193;
  
  public GeoObjectStatusMasterBase()
  {
    super();
  }
  
  public Integer getStatusOrder()
  {
    return com.runwaysdk.constants.MdAttributeIntegerUtil.getTypeSafeValue(getValue(STATUSORDER));
  }
  
  public void validateStatusOrder()
  {
    this.validateAttribute(STATUSORDER);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeIntegerDAOIF getStatusOrderMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.GeoObjectStatusMaster.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeIntegerDAOIF)mdClassIF.definesAttribute(STATUSORDER);
  }
  
  public void setStatusOrder(Integer value)
  {
    if(value == null)
    {
      setValue(STATUSORDER, "");
    }
    else
    {
      setValue(STATUSORDER, java.lang.Integer.toString(value));
    }
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public static GeoObjectStatusMasterQuery getAllInstances(String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    GeoObjectStatusMasterQuery query = new GeoObjectStatusMasterQuery(new com.runwaysdk.query.QueryFactory());
    com.runwaysdk.business.Entity.getAllInstances(query, sortAttribute, ascending, pageSize, pageNumber);
    return query;
  }
  
  public static GeoObjectStatusMaster get(String oid)
  {
    return (GeoObjectStatusMaster) com.runwaysdk.business.Business.get(oid);
  }
  
  public static GeoObjectStatusMaster getByKey(String key)
  {
    return (GeoObjectStatusMaster) com.runwaysdk.business.Business.get(CLASS, key);
  }
  
  public static GeoObjectStatusMaster getEnumeration(String enumName)
  {
    return (GeoObjectStatusMaster) com.runwaysdk.business.Business.getEnumeration(net.geoprism.registry.GeoObjectStatusMaster.CLASS ,enumName);
  }
  
  public static GeoObjectStatusMaster lock(java.lang.String oid)
  {
    GeoObjectStatusMaster _instance = GeoObjectStatusMaster.get(oid);
    _instance.lock();
    
    return _instance;
  }
  
  public static GeoObjectStatusMaster unlock(java.lang.String oid)
  {
    GeoObjectStatusMaster _instance = GeoObjectStatusMaster.get(oid);
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
