package net.geoprism.registry.task;

@com.runwaysdk.business.ClassSignature(hash = 1264422959)
public abstract class TaskMessageDTOBase extends com.runwaysdk.business.LocalStructDTO
{
  public final static String CLASS = "net.geoprism.registry.task.TaskMessage";
  private static final long serialVersionUID = 1264422959;
  
  protected TaskMessageDTOBase(com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(clientRequest);
  }
  
  /**
  * Copy Constructor: Duplicates the values and attributes of the given StructDTO into a new DTO.
  * 
  * @param structDTO The StructDTO to duplicate
  * @param clientRequest The clientRequest this DTO should use to communicate with the server.
  */
  protected TaskMessageDTOBase(com.runwaysdk.business.LocalStructDTO structDTO, com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(structDTO, clientRequest);
  }
  
  protected java.lang.String getDeclaredType()
  {
    return CLASS;
  }
  
  public static java.lang.String DEFAULTLOCALE = "defaultLocale";
  public static java.lang.String KEYNAME = "keyName";
  public static java.lang.String OID = "oid";
  public static java.lang.String SITEMASTER = "siteMaster";
  public String getKeyName()
  {
    return getValue(KEYNAME);
  }
  
  public void setKeyName(String value)
  {
    if(value == null)
    {
      setValue(KEYNAME, "");
    }
    else
    {
      setValue(KEYNAME, value);
    }
  }
  
  public boolean isKeyNameWritable()
  {
    return isWritable(KEYNAME);
  }
  
  public boolean isKeyNameReadable()
  {
    return isReadable(KEYNAME);
  }
  
  public boolean isKeyNameModified()
  {
    return isModified(KEYNAME);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeCharacterMdDTO getKeyNameMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeCharacterMdDTO) getAttributeDTO(KEYNAME).getAttributeMdDTO();
  }
  
  public String getSiteMaster()
  {
    return getValue(SITEMASTER);
  }
  
  public boolean isSiteMasterWritable()
  {
    return isWritable(SITEMASTER);
  }
  
  public boolean isSiteMasterReadable()
  {
    return isReadable(SITEMASTER);
  }
  
  public boolean isSiteMasterModified()
  {
    return isModified(SITEMASTER);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeCharacterMdDTO getSiteMasterMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeCharacterMdDTO) getAttributeDTO(SITEMASTER).getAttributeMdDTO();
  }
  
  public static TaskMessageDTO get(com.runwaysdk.constants.ClientRequestIF clientRequest, String oid)
  {
    com.runwaysdk.business.EntityDTO dto = (com.runwaysdk.business.EntityDTO)clientRequest.get(oid);
    
    return (TaskMessageDTO) dto;
  }
  
  public void apply()
  {
    if(isNewInstance())
    {
      getRequest().createStruct(this);
    }
    else
    {
      getRequest().update(this);
    }
  }
  public void delete()
  {
    getRequest().delete(this.getOid());
  }
  
  public static net.geoprism.registry.task.TaskMessageQueryDTO getAllInstances(com.runwaysdk.constants.ClientRequestIF clientRequest, String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    return (net.geoprism.registry.task.TaskMessageQueryDTO) clientRequest.getAllInstances(net.geoprism.registry.task.TaskMessageDTO.CLASS, sortAttribute, ascending, pageSize, pageNumber);
  }
  
}