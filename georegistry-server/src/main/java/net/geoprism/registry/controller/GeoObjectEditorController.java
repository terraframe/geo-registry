package net.geoprism.registry.controller;

import java.util.List;

import org.commongeoregistry.adapter.dataaccess.GeoObject;
import org.commongeoregistry.adapter.dataaccess.ParentTreeNode;
import org.json.JSONException;

import com.runwaysdk.constants.ClientRequestIF;
import com.runwaysdk.dataaccess.transaction.Transaction;
import com.runwaysdk.mvc.Controller;
import com.runwaysdk.mvc.Endpoint;
import com.runwaysdk.mvc.ErrorSerialization;
import com.runwaysdk.mvc.RequestParamter;
import com.runwaysdk.mvc.ResponseIF;
import com.runwaysdk.mvc.RestResponse;
import com.runwaysdk.session.Request;
import com.runwaysdk.session.RequestType;

import net.geoprism.registry.MasterList;
import net.geoprism.registry.service.RegistryService;
import net.geoprism.registry.service.ServiceFactory;

@Controller(url = "geoobject-editor")
public class GeoObjectEditorController
{
  @Endpoint(error = ErrorSerialization.JSON)
  public ResponseIF apply(ClientRequestIF request, @RequestParamter(name = "parentTreeNode") String parentTreeNode, @RequestParamter(name = "geoObject") String geoObject, @RequestParamter(name="masterListId") String masterListId) throws JSONException
  {
    apply(request.getSessionId(), parentTreeNode, geoObject, masterListId);
    
    return new RestResponse();
  }
  
  @Request(RequestType.SESSION)
  private void apply(String sessionId, String ptn, String go, String masterListId)
  {
    applyInTransaction(sessionId, ptn, go, masterListId);
  }
  @Transaction
  private void applyInTransaction(String sessionId, String sPtn, String sGo, String masterListId)
  {
    GeoObject go = RegistryService.getInstance().updateGeoObject(sessionId, sGo.toString());
    
    ParentTreeNode ptn = ParentTreeNode.fromJSON(sPtn.toString(), ServiceFactory.getAdapter());
    
    applyPtn(sessionId, ptn);
    
    // Update the master list record
    MasterList.get(masterListId).updateRecord(go);
  }
  private void applyPtn(String sessionId, ParentTreeNode ptn)
  {
    GeoObject child = ptn.getGeoObject();
    List<ParentTreeNode> childDbParents = RegistryService.getInstance().getParentGeoObjects(sessionId, child.getUid(), child.getType().getCode(), null, false).getParents();
    
    // Remove all existing relationships which aren't what we're trying to create
    for (ParentTreeNode childDbParent : childDbParents)
    {
      boolean shouldRemove = true;
      
      for (ParentTreeNode ptnParent : ptn.getParents())
      {
        if (ptnParent.equals(childDbParent) && ptnParent.getHierachyType().getCode().equals(childDbParent.getHierachyType().getCode()))
        {
          shouldRemove = false;
        }
      }
      
      if (shouldRemove)
      {
        RegistryService.getInstance().removeChild(sessionId, childDbParent.getGeoObject().getUid(), childDbParent.getGeoObject().getType().getCode(), child.getUid(), child.getType().getCode(), childDbParent.getHierachyType().getCode());
      }
    }
    
    // Create new relationships that don't already exist
    for (ParentTreeNode ptnParent : ptn.getParents())
    {
      boolean alreadyExists = false;
      
      for (ParentTreeNode ptnDbParent : childDbParents)
      {
        if (ptnParent.equals(ptnDbParent) && ptnParent.getHierachyType().getCode().equals(ptnDbParent.getHierachyType().getCode()))
        {
          alreadyExists = true;
        }
      }
      
      if (!alreadyExists)
      {
        GeoObject parent = ptnParent.getGeoObject();
        RegistryService.getInstance().addChild(sessionId, parent.getUid(), parent.getType().getCode(), child.getUid(), child.getType().getCode(), ptnParent.getHierachyType().getCode());
      }
    }
  }
}
