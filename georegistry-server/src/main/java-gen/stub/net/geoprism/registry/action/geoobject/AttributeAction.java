package net.geoprism.registry.action.geoobject;

import com.google.gson.JsonObject;

import net.geoprism.registry.model.ServerGeoObjectType;
import net.geoprism.registry.model.graph.VertexServerGeoObject;

public class AttributeAction extends AttributeActionBase
{
  private static final long serialVersionUID = 1813648932;
  
  public AttributeAction()
  {
    super();
  }

  public void execute(VertexServerGeoObject go)
  {
    go.setValue(this.getAttributeName(), this.getValue());
  }

  @Override
  public String getOrganizationCode()
  {
    return this.getAllParentAction().getAll().get(0).getOrganizationCode();
  }

  @Override
  public String getGeoObjectTypeCode()
  {
    return this.getAllParentAction().getAll().get(0).getGeoObjectTypeCode();
  }

  @Override
  public void execute()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean referencesType(ServerGeoObjectType type)
  {
    return this.getAllParentAction().getAll().get(0).referencesType(type);
  }

  @Override
  public String getMessage()
  {
    return this.getAllParentAction().getAll().get(0).getMessage();
  }

  @Override
  public JsonObject toJson()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
