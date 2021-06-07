package net.geoprism.registry.action.geoobject;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.runwaysdk.query.OIterator;
import com.runwaysdk.session.Session;

import net.geoprism.localization.LocalizationFacade;
import net.geoprism.registry.action.AbstractAction;
import net.geoprism.registry.action.ActionJsonAdapters;
import net.geoprism.registry.conversion.VertexGeoObjectStrategy;
import net.geoprism.registry.model.ServerGeoObjectType;
import net.geoprism.registry.model.graph.VertexServerGeoObject;
import net.geoprism.registry.service.ServiceFactory;

public class GeoObjectAction extends GeoObjectActionBase
{
  private static final long serialVersionUID = -1415152665;
  
  public GeoObjectAction()
  {
    super();
  }

  @Override
  public String getGeoObjectTypeCode()
  {
    return this.getGeoObjectTypeCode();
  }

  @Override
  public void execute()
  {
    ServerGeoObjectType type = ServerGeoObjectType.get(this.getGeoObjectTypeCode());
    VertexGeoObjectStrategy strategy = new VertexGeoObjectStrategy(type);
    VertexServerGeoObject go = strategy.getGeoObjectByCode(this.getGeoObjectCode());

    OIterator<? extends AbstractAction> it = this.getAllChildAction();
    
    for (AbstractAction action : it)
    {
      if (action instanceof AttributeAction)
      {
        ((AttributeAction)action).execute(go);
      }
    }
    
    go.apply(false);
  }

  @Override
  public boolean referencesType(ServerGeoObjectType type)
  {
    return this.getGeoObjectTypeCode().equals(type.getCode());
  }

  @Override
  public String getMessage()
  {
    ServerGeoObjectType got = ServiceFactory.getMetadataCache().getGeoObjectType(this.getGeoObjectTypeCode()).get();

    String message = LocalizationFacade.getFromBundles("change.request.email.update.object");
    message = message.replaceAll("\\{0\\}", this.getGeoObjectCode());
    message = message.replaceAll("\\{1\\}", got.getLabel().getValue(Session.getCurrentLocale()));

    return message;
  }

  @Override
  public JsonObject toJson()
  {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(GeoObjectAction.class, new ActionJsonAdapters.GeoObjectActionSerializer());

    return (JsonObject) builder.create().toJsonTree(this);
  }
  
}
