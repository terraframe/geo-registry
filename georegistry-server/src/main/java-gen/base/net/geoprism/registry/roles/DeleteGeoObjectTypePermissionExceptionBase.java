package net.geoprism.registry.roles;

@com.runwaysdk.business.ClassSignature(hash = -756298573)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to DeleteGeoObjectTypePermissionException.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class DeleteGeoObjectTypePermissionExceptionBase extends com.runwaysdk.business.SmartException
{
  public final static String CLASS = "net.geoprism.registry.roles.DeleteGeoObjectTypePermissionException";
  public static java.lang.String GEOOBJECTTYPE = "geoObjectType";
  public static java.lang.String OID = "oid";
  public static java.lang.String ORGANIZATION = "organization";
  private static final long serialVersionUID = -756298573;
  
  public DeleteGeoObjectTypePermissionExceptionBase()
  {
    super();
  }
  
  public DeleteGeoObjectTypePermissionExceptionBase(java.lang.String developerMessage)
  {
    super(developerMessage);
  }
  
  public DeleteGeoObjectTypePermissionExceptionBase(java.lang.String developerMessage, java.lang.Throwable cause)
  {
    super(developerMessage, cause);
  }
  
  public DeleteGeoObjectTypePermissionExceptionBase(java.lang.Throwable cause)
  {
    super(cause);
  }
  
  public String getGeoObjectType()
  {
    return getValue(GEOOBJECTTYPE);
  }
  
  public void validateGeoObjectType()
  {
    this.validateAttribute(GEOOBJECTTYPE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getGeoObjectTypeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.roles.DeleteGeoObjectTypePermissionException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(GEOOBJECTTYPE);
  }
  
  public void setGeoObjectType(String value)
  {
    if(value == null)
    {
      setValue(GEOOBJECTTYPE, "");
    }
    else
    {
      setValue(GEOOBJECTTYPE, value);
    }
  }
  
  public String getOid()
  {
    return getValue(OID);
  }
  
  public void validateOid()
  {
    this.validateAttribute(OID);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF getOidMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.roles.DeleteGeoObjectTypePermissionException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF)mdClassIF.definesAttribute(OID);
  }
  
  public String getOrganization()
  {
    return getValue(ORGANIZATION);
  }
  
  public void validateOrganization()
  {
    this.validateAttribute(ORGANIZATION);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getOrganizationMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.roles.DeleteGeoObjectTypePermissionException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(ORGANIZATION);
  }
  
  public void setOrganization(String value)
  {
    if(value == null)
    {
      setValue(ORGANIZATION, "");
    }
    else
    {
      setValue(ORGANIZATION, value);
    }
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public java.lang.String localize(java.util.Locale locale)
  {
    java.lang.String message = super.localize(locale);
    message = replace(message, "{geoObjectType}", this.getGeoObjectType());
    message = replace(message, "{oid}", this.getOid());
    message = replace(message, "{organization}", this.getOrganization());
    return message;
  }
  
}
