package net.geoprism.registry;

@com.runwaysdk.business.ClassSignature(hash = -1523682972)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to GeoObjectTypeAssignmentException.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class GeoObjectTypeAssignmentExceptionBase extends com.runwaysdk.business.SmartException
{
  public final static String CLASS = "net.geoprism.registry.GeoObjectTypeAssignmentException";
  public static java.lang.String OID = "oid";
  private static final long serialVersionUID = -1523682972;
  
  public GeoObjectTypeAssignmentExceptionBase()
  {
    super();
  }
  
  public GeoObjectTypeAssignmentExceptionBase(java.lang.String developerMessage)
  {
    super(developerMessage);
  }
  
  public GeoObjectTypeAssignmentExceptionBase(java.lang.String developerMessage, java.lang.Throwable cause)
  {
    super(developerMessage, cause);
  }
  
  public GeoObjectTypeAssignmentExceptionBase(java.lang.Throwable cause)
  {
    super(cause);
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
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.GeoObjectTypeAssignmentException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF)mdClassIF.definesAttribute(OID);
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public java.lang.String localize(java.util.Locale locale)
  {
    java.lang.String message = super.localize(locale);
    message = replace(message, "{oid}", this.getOid());
    return message;
  }
  
}