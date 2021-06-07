package net.geoprism.registry.action.geoobject;

import java.util.Date;

import org.commongeoregistry.adapter.dataaccess.ValueOverTimeDTO;

import com.runwaysdk.dataaccess.ProgrammingErrorException;
import com.runwaysdk.dataaccess.graph.attributes.ValueOverTime;
import com.runwaysdk.dataaccess.graph.attributes.ValueOverTimeCollection;

import net.geoprism.registry.action.StaleChangeRequestException;
import net.geoprism.registry.model.graph.VertexServerGeoObject;

public class ValueOverTimeAttributeAction extends ValueOverTimeAttributeActionBase
{
  private static final long serialVersionUID = -1834238961;
  
  public ValueOverTimeAttributeAction()
  {
    super();
  }
  
  @Override
  public void execute(VertexServerGeoObject go)
  {
    String votAction = this.getAction();
    
    final ValueOverTimeCollection votc = go.getValuesOverTime(this.getAttributeName());
    
    final ValueOverTime vot = getAtStartDate(votc, this.getOldStartDate());
    
    if (votAction.equals("CREATE"))
    {
      if (vot != null)
      {
        throw new StaleChangeRequestException();
      }
      
      go.setValue(this.getAttributeName(), this.getValue(), this.getNewStartDate(), this.getNewEndDate());
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
  
  protected ValueOverTime getAtStartDate(ValueOverTimeCollection votc, Date date)
  {
    Date localDate = ValueOverTimeDTO.toLocal(date);
    
    for (ValueOverTime vot : votc)
    {
      if (vot.getStartDate().equals(localDate))
      {
        return vot;
      }
    }
    
    return null;
  }
}
