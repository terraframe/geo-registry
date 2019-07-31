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
package net.geoprism.registry.action.geoobject;

@com.runwaysdk.business.ClassSignature(hash = 1266073895)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to CreateGeoObjectAction.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class CreateGeoObjectActionBase extends net.geoprism.registry.action.AbstractAction
{
  public final static String CLASS = "net.geoprism.registry.action.geoobject.CreateGeoObjectAction";
  public static java.lang.String GEOOBJECTJSON = "geoObjectJson";
  private static final long serialVersionUID = 1266073895;
  
  public CreateGeoObjectActionBase()
  {
    super();
  }
  
  public String getGeoObjectJson()
  {
    return getValue(GEOOBJECTJSON);
  }
  
  public void validateGeoObjectJson()
  {
    this.validateAttribute(GEOOBJECTJSON);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getGeoObjectJsonMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.geoobject.CreateGeoObjectAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(GEOOBJECTJSON);
  }
  
  public void setGeoObjectJson(String value)
  {
    if(value == null)
    {
      setValue(GEOOBJECTJSON, "");
    }
    else
    {
      setValue(GEOOBJECTJSON, value);
    }
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public static CreateGeoObjectAction get(String oid)
  {
    return (CreateGeoObjectAction) com.runwaysdk.business.Business.get(oid);
  }
  
  public static CreateGeoObjectAction getByKey(String key)
  {
    return (CreateGeoObjectAction) com.runwaysdk.business.Business.get(CLASS, key);
  }
  
  public static CreateGeoObjectAction lock(java.lang.String oid)
  {
    CreateGeoObjectAction _instance = CreateGeoObjectAction.get(oid);
    _instance.lock();
    
    return _instance;
  }
  
  public static CreateGeoObjectAction unlock(java.lang.String oid)
  {
    CreateGeoObjectAction _instance = CreateGeoObjectAction.get(oid);
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
