package net.geoprism.registry.action.geoobject;

import org.commongeoregistry.adapter.dataaccess.GeoObjectOverTime;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.runwaysdk.query.OIterator;
import com.runwaysdk.session.Session;

import net.geoprism.localization.LocalizationFacade;
import net.geoprism.registry.action.ActionJsonAdapters;
import net.geoprism.registry.geoobject.ServerGeoObjectService;
import net.geoprism.registry.model.ServerGeoObjectType;
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
    GeoObjectOverTime goTime = ServiceFactory.getRegistryService().getGeoObjectOverTimeByCode(Session.getCurrentSession().getOid(), this.getGeoObjectCode(), this.getGeoObjectTypeCode());

    OIterator<? extends AttributeAction> it = this.getAllAttributeAction();
    
    for (AttributeAction action : it)
    {
      action.execute(goTime);
    }
    
    ServerGeoObjectService builder = new ServerGeoObjectService();
    builder.apply(goTime, false, false);
  }

  @Override
  public boolean referencesType(ServerGeoObjectType type)
  {
    return this.getGeoObjectTypeCode().equals(type.getCode());
  }

  @Override
  protected String getMessage()
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
