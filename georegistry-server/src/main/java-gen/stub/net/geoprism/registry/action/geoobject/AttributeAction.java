package net.geoprism.registry.action.geoobject;

import org.commongeoregistry.adapter.dataaccess.GeoObjectOverTime;

public class AttributeAction extends AttributeActionBase
{
  private static final long serialVersionUID = 1813648932;
  
  public AttributeAction()
  {
    super();
  }

  public void execute(GeoObjectOverTime goTime)
  {
    goTime.setValue(this.getAttributeName(), this.getValue());
  }
}
