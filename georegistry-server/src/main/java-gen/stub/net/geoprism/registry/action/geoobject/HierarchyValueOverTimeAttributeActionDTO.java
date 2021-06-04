package net.geoprism.registry.action.geoobject;

public class HierarchyValueOverTimeAttributeActionDTO extends HierarchyValueOverTimeAttributeActionDTOBase
{
  private static final long serialVersionUID = 1203027536;
  
  public HierarchyValueOverTimeAttributeActionDTO(com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(clientRequest);
  }
  
  /**
  * Copy Constructor: Duplicates the values and attributes of the given BusinessDTO into a new DTO.
  * 
  * @param businessDTO The BusinessDTO to duplicate
  * @param clientRequest The clientRequest this DTO should use to communicate with the server.
  */
  protected HierarchyValueOverTimeAttributeActionDTO(com.runwaysdk.business.BusinessDTO businessDTO, com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(businessDTO, clientRequest);
  }
  
}
