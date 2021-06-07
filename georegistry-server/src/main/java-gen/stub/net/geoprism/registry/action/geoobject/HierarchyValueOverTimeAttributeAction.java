package net.geoprism.registry.action.geoobject;

import java.util.SortedSet;

import org.commongeoregistry.adapter.dataaccess.ValueOverTimeCollectionDTO;
import org.commongeoregistry.adapter.dataaccess.ValueOverTimeDTO;

import com.runwaysdk.business.graph.EdgeObject;
import com.runwaysdk.dataaccess.ProgrammingErrorException;

import net.geoprism.registry.action.StaleChangeRequestException;
import net.geoprism.registry.model.graph.VertexServerGeoObject;

public class HierarchyValueOverTimeAttributeAction extends HierarchyValueOverTimeAttributeActionBase
{
  private static final long serialVersionUID = 753823696;
  
  public HierarchyValueOverTimeAttributeAction()
  {
    super();
  }
  
  @Override
  public void execute(VertexServerGeoObject go)
  {
    // TODO
    
    String votAction = this.getAction();
    
//    final ValueOverTimeCollectionDTO votc = goTime.getAllValues(this.getAttributeName());
    final SortedSet<EdgeObject> edges = go.getEdges(hierarchyType);    
    final ValueOverTimeDTO vot = votc.getAtStartDate(this.getOldStartDate());
    
    if (votAction.equals("CREATE"))
    {
      if (vot != null)
      {
        throw new StaleChangeRequestException();
      }
      
      goTime.setValue(this.getAttributeName(), this.getValue(), this.getNewStartDate(), this.getNewEndDate());
    }
    else if (votAction.equals("DELETE"))
    {
      if (vot != null)
      {
        votc.remove(vot);
        return;
      }
      
      throw new StaleChangeRequestException();
    }
    else if (votAction.equals("UPDATE"))
    {
      if (vot == null)
      {
        throw new StaleChangeRequestException();
      }
      
      if (this.getNewStartDate() != null)
      {
        vot.setStartDate(this.getNewStartDate());
      }
      
      if (this.getNewEndDate() != null)
      {
        vot.setEndDate(this.getNewEndDate());
      }
      
      if (this.getValue() != null)
      {
        vot.setValue(this.getValue());
      }
    }
    else
    {
      throw new ProgrammingErrorException("Unexpected action " + votAction);
    }
  }
}
