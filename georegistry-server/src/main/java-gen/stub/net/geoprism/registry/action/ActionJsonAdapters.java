/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Geoprism Registry(tm).
 *
 * Geoprism Registry(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Geoprism Registry(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Geoprism Registry(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package net.geoprism.registry.action;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.commongeoregistry.adapter.Optional;
import org.commongeoregistry.adapter.constants.GeometryType;
import org.commongeoregistry.adapter.dataaccess.LocalizedValue;
import org.commongeoregistry.adapter.metadata.GeoObjectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.runwaysdk.session.Session;
import com.runwaysdk.system.SingleActor;
import com.runwaysdk.system.Users;

import net.geoprism.registry.action.ChangeRequestPermissionService.ChangeRequestPermissionAction;
import net.geoprism.registry.action.geoobject.CreateGeoObjectAction;
import net.geoprism.registry.action.geoobject.GeoObjectAction;
import net.geoprism.registry.io.GeoObjectImportConfiguration;
import net.geoprism.registry.service.ChangeRequestService;
import net.geoprism.registry.service.ServiceFactory;

public class ActionJsonAdapters
{

  abstract public static class AbstractActionDeserializer
  {
    public AbstractAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
      return null;
    }
  }
  
  abstract public static class AbstractActionSerializer
  {
    private ChangeRequestService service = new ChangeRequestService();
    
    private ChangeRequestPermissionService perms = new ChangeRequestPermissionService();
    
    public JsonElement serialize(AbstractAction action, Type typeOfSrc, JsonSerializationContext context)
    {
      DateFormat format = new SimpleDateFormat(GeoObjectImportConfiguration.DATE_FORMAT);
      
      AllGovernanceStatus status = action.getApprovalStatus().get(0);

      JsonObject jo = new JsonObject();

      jo.addProperty(AbstractAction.OID, action.getOid());
      jo.addProperty("actionType", action.getType());
      jo.addProperty("actionLabel", action.getMdClass().getDisplayLabel(Session.getCurrentLocale()));
      jo.addProperty(AbstractAction.CREATEACTIONDATE, format.format(action.getCreateActionDate()));
      jo.addProperty(AbstractAction.CONTRIBUTORNOTES, action.getContributorNotes());
      jo.addProperty(AbstractAction.MAINTAINERNOTES, action.getMaintainerNotes());
      jo.addProperty(AbstractAction.ADDITIONALNOTES, action.getAdditionalNotes());
      jo.addProperty(AbstractAction.APPROVALSTATUS, action.getApprovalStatus().get(0).getEnumName());
      jo.addProperty("statusLabel", status.getDisplayLabel());
      jo.addProperty(AbstractAction.CREATEACTIONDATE, format.format(action.getCreateActionDate()));
      
      ChangeRequestJsonAdapters.serializeCreatedBy(action.getCreatedBy(), jo);

      SingleActor decisionMaker = action.getDecisionMaker();

      if (decisionMaker != null && ( decisionMaker instanceof Users ))
      {
        jo.addProperty(AbstractAction.DECISIONMAKER, ( (Users) decisionMaker ).getUsername());
      }
      
      JsonArray jaDocuments = JsonParser.parseString(this.service.listDocumentsAction(Session.getCurrentSession().getOid(), action.getOid())).getAsJsonArray();
      jo.add("documents", jaDocuments);

      jo.add("permissions", this.serializePermissions(action, context));
      
      return jo;
    }
    
    protected JsonArray serializePermissions(AbstractAction action, JsonSerializationContext context)
    {
      Set<ChangeRequestPermissionAction> crPerms = this.perms.getPermissions(action);
      
      return context.serialize(crPerms).getAsJsonArray();
    }
  }
  
  abstract public static class AbstractGeoObjectActionSerializer extends AbstractActionSerializer
  {
    public JsonElement serialize(GeoObjectAction action, Type typeOfSrc, JsonSerializationContext context)
    {
      JsonObject jo = super.serialize(action, typeOfSrc, context).getAsJsonObject();
      
      jo.addProperty(GeoObjectAction.GEOOBJECTCODE, action.getGeoObjectCode());
      jo.addProperty(GeoObjectAction.GEOOBJECTTYPECODE, action.getGeoObjectTypeCode());
      jo.addProperty(GeoObjectAction.ORGANIZATIONCODE, action.getOrganizationCode());
      
      this.addGeoObjectType(action, jo);
      
      return jo;
    }
    
    private void addGeoObjectType(AbstractAction action, JsonObject object)
    {
      String typeCode = action.getGeoObjectTypeCode();
      
      Optional<GeoObjectType> op = ServiceFactory.getAdapter().getMetadataCache().getGeoObjectType(typeCode);
  
      GeoObjectType got;
      if (op.isPresent())
      {
        got = op.get();
      }
      else
      {
        got = new GeoObjectType(typeCode, GeometryType.POLYGON, new LocalizedValue(typeCode), new LocalizedValue(""), false, "", ServiceFactory.getAdapter());
      }
      
      object.add("geoObjectType", got.toJSON());
    }
  } 
  
  public static class GeoObjectActionSerializer extends AbstractGeoObjectActionSerializer implements JsonSerializer<GeoObjectAction> {}
  
  public static class CreateGeoObjectActionSerializer extends AbstractGeoObjectActionSerializer implements JsonSerializer<CreateGeoObjectAction>
  {
    public JsonElement serialize(CreateGeoObjectAction action, Type typeOfSrc, JsonSerializationContext context)
    {
      JsonObject jo = super.serialize(action, typeOfSrc, context).getAsJsonObject();
      
      jo.add(CreateGeoObjectAction.GEOOBJECTJSON, this.getGeoObjectJson(action));
      jo.addProperty(CreateGeoObjectAction.HIERARCHYJSON, action.getHierarchyJson());
      jo.addProperty(CreateGeoObjectAction.HIERARCHYCODE, action.getHierarchyCode());
      
      return jo;
    }

    private JsonObject getGeoObjectJson(AbstractAction action)
    {
      return JsonParser.parseString(((CreateGeoObjectAction)action).getGeoObjectJson()).getAsJsonObject();
    }
  }
}
