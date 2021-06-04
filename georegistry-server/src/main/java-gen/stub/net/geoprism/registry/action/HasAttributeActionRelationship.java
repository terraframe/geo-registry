package net.geoprism.registry.action;

public class HasAttributeActionRelationship extends HasAttributeActionRelationshipBase
{
  private static final long serialVersionUID = -89100620;
  
  public HasAttributeActionRelationship(String parentOid, String childOid)
  {
    super(parentOid, childOid);
  }
  
  public HasAttributeActionRelationship(net.geoprism.registry.action.geoobject.GeoObjectAction parent, net.geoprism.registry.action.geoobject.AttributeAction child)
  {
    this(parent.getOid(), child.getOid());
  }
  
}
