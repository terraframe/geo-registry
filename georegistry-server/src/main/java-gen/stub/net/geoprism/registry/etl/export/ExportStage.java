package net.geoprism.registry.etl.export;

/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 *
 * @author Autogenerated by RunwaySDK
 */
@com.runwaysdk.business.ClassSignature(hash = -977425651)
public enum ExportStage implements com.runwaysdk.business.BusinessEnumeration
{
  COMPLETE(),
  
  CONNECTING(),
  
  CONNECTION_FAILED(),
  
  EXPORT(),
  
  EXPORT_RESOLVE(),
  
  RESUME_EXPORT();
  
  public static final java.lang.String CLASS = "net.geoprism.registry.etl.export.ExportStage";
  private net.geoprism.registry.etl.export.ExportStageMaster enumeration;
  
  private synchronized void loadEnumeration()
  {
    net.geoprism.registry.etl.export.ExportStageMaster enu = net.geoprism.registry.etl.export.ExportStageMaster.getEnumeration(this.name());
    setEnumeration(enu);
  }
  
  private synchronized void setEnumeration(net.geoprism.registry.etl.export.ExportStageMaster enumeration)
  {
    this.enumeration = enumeration;
  }
  
  public java.lang.String getOid()
  {
    loadEnumeration();
    return enumeration.getOid();
  }
  
  public java.lang.String getEnumName()
  {
    loadEnumeration();
    return enumeration.getEnumName();
  }
  
  public java.lang.String getDisplayLabel()
  {
    loadEnumeration();
    return enumeration.getDisplayLabel().getValue(com.runwaysdk.session.Session.getCurrentLocale());
  }
  
  public static ExportStage get(String oid)
  {
    for (ExportStage e : ExportStage.values())
    {
      if (e.getOid().equals(oid))
      {
        return e;
      }
    }
    return null;
  }
  
}
