package net.geoprism.registry.action.geoobject;

@com.runwaysdk.business.ClassSignature(hash = -852706543)
public abstract class HierarchyValueOverTimeAttributeActionDTOBase extends net.geoprism.registry.action.geoobject.ValueOverTimeAttributeActionDTO
{
  public final static String CLASS = "net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeAction";
  private static final long serialVersionUID = -852706543;
  
  protected HierarchyValueOverTimeAttributeActionDTOBase(com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(clientRequest);
  }
  
  /**
  * Copy Constructor: Duplicates the values and attributes of the given BusinessDTO into a new DTO.
  * 
  * @param businessDTO The BusinessDTO to duplicate
  * @param clientRequest The clientRequest this DTO should use to communicate with the server.
  */
  protected HierarchyValueOverTimeAttributeActionDTOBase(com.runwaysdk.business.BusinessDTO businessDTO, com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(businessDTO, clientRequest);
  }
  
  protected java.lang.String getDeclaredType()
  {
    return CLASS;
  }
  
  public static java.lang.String HIERARCHYCODE = "hierarchyCode";
  public String getHierarchyCode()
  {
    return getValue(HIERARCHYCODE);
  }
  
  public void setHierarchyCode(String value)
  {
    if(value == null)
    {
      setValue(HIERARCHYCODE, "");
    }
    else
    {
      setValue(HIERARCHYCODE, value);
    }
  }
  
  public boolean isHierarchyCodeWritable()
  {
    return isWritable(HIERARCHYCODE);
  }
  
  public boolean isHierarchyCodeReadable()
  {
    return isReadable(HIERARCHYCODE);
  }
  
  public boolean isHierarchyCodeModified()
  {
    return isModified(HIERARCHYCODE);
  }
  
  public final com.runwaysdk.transport.metadata.AttributeTextMdDTO getHierarchyCodeMd()
  {
    return (com.runwaysdk.transport.metadata.AttributeTextMdDTO) getAttributeDTO(HIERARCHYCODE).getAttributeMdDTO();
  }
  
  public static net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO get(com.runwaysdk.constants.ClientRequestIF clientRequest, String oid)
  {
    com.runwaysdk.business.EntityDTO dto = (com.runwaysdk.business.EntityDTO)clientRequest.get(oid);
    
    return (net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO) dto;
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
  
  public static net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionQueryDTO getAllInstances(com.runwaysdk.constants.ClientRequestIF clientRequest, String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    return (net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionQueryDTO) clientRequest.getAllInstances(net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO.CLASS, sortAttribute, ascending, pageSize, pageNumber);
  }
  
  public void lock()
  {
    getRequest().lock(this);
  }
  
  public static net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO lock(com.runwaysdk.constants.ClientRequestIF clientRequest, java.lang.String oid)
  {
    String[] _declaredTypes = new String[]{"java.lang.String"};
    Object[] _parameters = new Object[]{oid};
    com.runwaysdk.business.MethodMetaData _metadata = new com.runwaysdk.business.MethodMetaData(net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO.CLASS, "lock", _declaredTypes);
    return (net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO) clientRequest.invokeMethod(_metadata, null, _parameters);
  }
  
  public void unlock()
  {
    getRequest().unlock(this);
  }
  
  public static net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO unlock(com.runwaysdk.constants.ClientRequestIF clientRequest, java.lang.String oid)
  {
    String[] _declaredTypes = new String[]{"java.lang.String"};
    Object[] _parameters = new Object[]{oid};
    com.runwaysdk.business.MethodMetaData _metadata = new com.runwaysdk.business.MethodMetaData(net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO.CLASS, "unlock", _declaredTypes);
    return (net.geoprism.registry.action.geoobject.HierarchyValueOverTimeAttributeActionDTO) clientRequest.invokeMethod(_metadata, null, _parameters);
  }
  
}
