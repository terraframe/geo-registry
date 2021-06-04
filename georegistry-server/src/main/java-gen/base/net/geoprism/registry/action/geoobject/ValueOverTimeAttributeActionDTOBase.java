package net.geoprism.registry.action.geoobject;

@com.runwaysdk.business.ClassSignature(hash = 1630816078)
public abstract class ValueOverTimeAttributeActionDTOBase extends net.geoprism.registry.action.geoobject.AttributeActionDTO
{
  public final static String CLASS = "net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction";
  private static final long serialVersionUID = 1630816078;
  
  protected ValueOverTimeAttributeActionDTOBase(com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(clientRequest);
  }
  
  /**
  * Copy Constructor: Duplicates the values and attributes of the given BusinessDTO into a new DTO.
  * 
  * @param businessDTO The BusinessDTO to duplicate
  * @param clientRequest The clientRequest this DTO should use to communicate with the server.
  */
  protected ValueOverTimeAttributeActionDTOBase(com.runwaysdk.business.BusinessDTO businessDTO, com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(businessDTO, clientRequest);
  }
  
  protected java.lang.String getDeclaredType()
  {
    return CLASS;
  }
  
  public static java.lang.String ACTION = "action";
  public static java.lang.String NEWENDDATE = "newEndDate";
  public static java.lang.String NEWSTARTDATE = "newStartDate";
  public static java.lang.String OLDENDDATE = "oldEndDate";
  public static java.lang.String OLDSTARTDATE = "oldStartDate";
  public String getAction()
  {
    return getValue(ACTION);
  }
  
  public void setAction(String value)
  {
    if(value == null)
    {
      setValue(ACTION, "");
    }
    else
    {
      setValue(ACTION, value);
    }
  }
  
  public boolean isActionWritable()
  {
    return isWritable(ACTION);
  }
  
  public boolean isActionReadable()
  {
    return isReadable(ACTION);
  }
  
  public boolean isActionModified()
  {
    return isModified(ACTION);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeTextMdDTO getActionMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeTextMdDTO) getAttributeDTO(ACTION).getAttributeMdDTO();
  }
  
  public java.util.Date getNewEndDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(NEWENDDATE));
  }
  
  public void setNewEndDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(NEWENDDATE, "");
    }
    else
    {
      setValue(NEWENDDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public boolean isNewEndDateWritable()
  {
    return isWritable(NEWENDDATE);
  }
  
  public boolean isNewEndDateReadable()
  {
    return isReadable(NEWENDDATE);
  }
  
  public boolean isNewEndDateModified()
  {
    return isModified(NEWENDDATE);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeDateMdDTO getNewEndDateMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeDateMdDTO) getAttributeDTO(NEWENDDATE).getAttributeMdDTO();
  }
  
  public java.util.Date getNewStartDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(NEWSTARTDATE));
  }
  
  public void setNewStartDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(NEWSTARTDATE, "");
    }
    else
    {
      setValue(NEWSTARTDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public boolean isNewStartDateWritable()
  {
    return isWritable(NEWSTARTDATE);
  }
  
  public boolean isNewStartDateReadable()
  {
    return isReadable(NEWSTARTDATE);
  }
  
  public boolean isNewStartDateModified()
  {
    return isModified(NEWSTARTDATE);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeDateMdDTO getNewStartDateMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeDateMdDTO) getAttributeDTO(NEWSTARTDATE).getAttributeMdDTO();
  }
  
  public java.util.Date getOldEndDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(OLDENDDATE));
  }
  
  public void setOldEndDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(OLDENDDATE, "");
    }
    else
    {
      setValue(OLDENDDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public boolean isOldEndDateWritable()
  {
    return isWritable(OLDENDDATE);
  }
  
  public boolean isOldEndDateReadable()
  {
    return isReadable(OLDENDDATE);
  }
  
  public boolean isOldEndDateModified()
  {
    return isModified(OLDENDDATE);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeDateMdDTO getOldEndDateMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeDateMdDTO) getAttributeDTO(OLDENDDATE).getAttributeMdDTO();
  }
  
  public java.util.Date getOldStartDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(OLDSTARTDATE));
  }
  
  public void setOldStartDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(OLDSTARTDATE, "");
    }
    else
    {
      setValue(OLDSTARTDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public boolean isOldStartDateWritable()
  {
    return isWritable(OLDSTARTDATE);
  }
  
  public boolean isOldStartDateReadable()
  {
    return isReadable(OLDSTARTDATE);
  }
  
  public boolean isOldStartDateModified()
  {
    return isModified(OLDSTARTDATE);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeDateMdDTO getOldStartDateMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeDateMdDTO) getAttributeDTO(OLDSTARTDATE).getAttributeMdDTO();
  }
  
  public static net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO get(com.runwaysdk.constants.ClientRequestIF clientRequest, String oid)
  {
    com.runwaysdk.business.EntityDTO dto = (com.runwaysdk.business.EntityDTO)clientRequest.get(oid);
    
    return (net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO) dto;
  }
  
  public void apply()
  {
    if(isNewInstance())
    {
      getRequest().createBusiness(this);
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
  
  public static net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionQueryDTO getAllInstances(com.runwaysdk.constants.ClientRequestIF clientRequest, String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    return (net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionQueryDTO) clientRequest.getAllInstances(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO.CLASS, sortAttribute, ascending, pageSize, pageNumber);
  }
  
  public void lock()
  {
    getRequest().lock(this);
  }
  
  public static net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO lock(com.runwaysdk.constants.ClientRequestIF clientRequest, java.lang.String oid)
  {
    String[] _declaredTypes = new String[]{"java.lang.String"};
    Object[] _parameters = new Object[]{oid};
    com.runwaysdk.business.MethodMetaData _metadata = new com.runwaysdk.business.MethodMetaData(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO.CLASS, "lock", _declaredTypes);
    return (net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO) clientRequest.invokeMethod(_metadata, null, _parameters);
  }
  
  public void unlock()
  {
    getRequest().unlock(this);
  }
  
  public static net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO unlock(com.runwaysdk.constants.ClientRequestIF clientRequest, java.lang.String oid)
  {
    String[] _declaredTypes = new String[]{"java.lang.String"};
    Object[] _parameters = new Object[]{oid};
    com.runwaysdk.business.MethodMetaData _metadata = new com.runwaysdk.business.MethodMetaData(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO.CLASS, "unlock", _declaredTypes);
    return (net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO) clientRequest.invokeMethod(_metadata, null, _parameters);
  }
  
}
