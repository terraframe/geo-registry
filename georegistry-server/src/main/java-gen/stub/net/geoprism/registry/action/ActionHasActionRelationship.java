package net.geoprism.registry.action;

public class ActionHasActionRelationship extends ActionHasActionRelationshipBase
{
  private static final long serialVersionUID = -845297673;
  
  public ActionHasActionRelationship(String parentOid, String childOid)
  {
    super(parentOid, childOid);
  }
  
  public ActionHasActionRelationship(net.geoprism.registry.action.AbstractAction parent, net.geoprism.registry.action.AbstractAction child)
  {
    this(parent.getOid(), child.getOid());
  }
  
}
