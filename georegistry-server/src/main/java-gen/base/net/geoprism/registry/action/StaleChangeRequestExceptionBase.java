package net.geoprism.registry.action;

@com.runwaysdk.business.ClassSignature(hash = 604685277)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to StaleChangeRequestException.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class StaleChangeRequestExceptionBase extends com.runwaysdk.business.SmartException
{
  public final static String CLASS = "net.geoprism.registry.action.StaleChangeRequestException";
  public static java.lang.String OID = "oid";
  private static final long serialVersionUID = 604685277;
  
  public StaleChangeRequestExceptionBase()
  {
    super();
  }
  
  public StaleChangeRequestExceptionBase(java.lang.String developerMessage)
  {
    super(developerMessage);
  }
  
  public StaleChangeRequestExceptionBase(java.lang.String developerMessage, java.lang.Throwable cause)
  {
    super(developerMessage, cause);
  }
  
  public StaleChangeRequestExceptionBase(java.lang.Throwable cause)
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
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.StaleChangeRequestException.CLASS);
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
