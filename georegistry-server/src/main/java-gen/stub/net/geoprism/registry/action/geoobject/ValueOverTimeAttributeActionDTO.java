package net.geoprism.registry.action.geoobject;

public class ValueOverTimeAttributeActionDTO extends ValueOverTimeAttributeActionDTOBase
{
  private static final long serialVersionUID = 1331415695;
  
  public ValueOverTimeAttributeActionDTO(com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(clientRequest);
  }
  
  /**
  * Copy Constructor: Duplicates the values and attributes of the given BusinessDTO into a new DTO.
  * 
  * @param businessDTO The BusinessDTO to duplicate
  * @param clientRequest The clientRequest this DTO should use to communicate with the server.
  */
  protected ValueOverTimeAttributeActionDTO(com.runwaysdk.business.BusinessDTO businessDTO, com.runwaysdk.constants.ClientRequestIF clientRequest)
  {
    super(businessDTO, clientRequest);
  }
  
}
