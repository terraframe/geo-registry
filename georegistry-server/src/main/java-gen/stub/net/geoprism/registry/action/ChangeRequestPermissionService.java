package net.geoprism.registry.action;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.runwaysdk.dataaccess.ProgrammingErrorException;

import net.geoprism.registry.permission.RolePermissionService;
import net.geoprism.registry.service.ServiceFactory;

public class ChangeRequestPermissionService
{
  public static enum ChangeRequestPermissionAction {
    EXECUTE, WRITE_APPROVAL_STATUS, READ_APPROVAL_STATUS, READ_DETAILS, WRITE_DETAILS, READ_DOCUMENTS, WRITE_DOCUMENTS,
    READ_CONTRIBUTOR_NOTES, WRITE_CONTRIBUTOR_NOTES, READ_MAINTAINER_NOTES, WRITE_MAINTAINER_NOTES, READ, WRITE, SUBMIT,
    DELETE
  }
  
  public Set<ChangeRequestPermissionAction> getPermissions(GovernancePermissionEntity cr)
  {
    final RolePermissionService perms = ServiceFactory.getRolePermissionService();
    
    final String orgCode = cr.getOrganization();
    final String gotCode = cr.getGeoObjectType();
    
    if (orgCode == null || gotCode == null)
    {
      throw new ProgrammingErrorException("These should not be null: [" + orgCode + "] [" + gotCode + "]");
    }
    
    final AllGovernanceStatus status = cr.getGovernanceStatus();
    
    if (perms.isSRA())
    {
      HashSet<ChangeRequestPermissionAction> actions = new HashSet<ChangeRequestPermissionAction>(Arrays.asList(ChangeRequestPermissionAction.values()));
      
      actions.remove(ChangeRequestPermissionAction.DELETE);
      actions.remove(ChangeRequestPermissionAction.WRITE_CONTRIBUTOR_NOTES);
      actions.remove(ChangeRequestPermissionAction.WRITE_DETAILS);
      
      if (status.equals(AllGovernanceStatus.ACCEPTED))
      {
        actions.remove(ChangeRequestPermissionAction.EXECUTE);
        actions.remove(ChangeRequestPermissionAction.WRITE_MAINTAINER_NOTES);
      }
      
      return actions;
    }
    else if (perms.isRA(orgCode))
    {
      HashSet<ChangeRequestPermissionAction> actions = new HashSet<ChangeRequestPermissionAction>(Arrays.asList(ChangeRequestPermissionAction.values()));
      
      actions.remove(ChangeRequestPermissionAction.DELETE);
      actions.remove(ChangeRequestPermissionAction.WRITE_CONTRIBUTOR_NOTES);
      actions.remove(ChangeRequestPermissionAction.WRITE_DETAILS);
      
      if (status.equals(AllGovernanceStatus.ACCEPTED))
      {
        actions.remove(ChangeRequestPermissionAction.EXECUTE);
        actions.remove(ChangeRequestPermissionAction.WRITE_MAINTAINER_NOTES);
      }
      
      return actions;
    }
    else if (perms.isRM(orgCode, gotCode))
    {
      HashSet<ChangeRequestPermissionAction> actions = new HashSet<ChangeRequestPermissionAction>(Arrays.asList(ChangeRequestPermissionAction.values()));
      
      actions.remove(ChangeRequestPermissionAction.DELETE);
      actions.remove(ChangeRequestPermissionAction.WRITE_CONTRIBUTOR_NOTES);
      actions.remove(ChangeRequestPermissionAction.WRITE_DETAILS);
      
      if (status.equals(AllGovernanceStatus.ACCEPTED))
      {
        actions.remove(ChangeRequestPermissionAction.EXECUTE);
        actions.remove(ChangeRequestPermissionAction.WRITE_MAINTAINER_NOTES);
      }
      
      return actions;
    }
    else if (perms.isRC(orgCode, gotCode) || perms.isAC(orgCode, gotCode))
    {
      HashSet<ChangeRequestPermissionAction> actions = new HashSet<ChangeRequestPermissionAction>();
      
      actions.addAll(Arrays.asList(ChangeRequestPermissionAction.READ, ChangeRequestPermissionAction.WRITE, ChangeRequestPermissionAction.READ_APPROVAL_STATUS, ChangeRequestPermissionAction.READ_DETAILS,
          ChangeRequestPermissionAction.WRITE_DETAILS, ChangeRequestPermissionAction.READ_DOCUMENTS, ChangeRequestPermissionAction.WRITE_DOCUMENTS, ChangeRequestPermissionAction.READ_MAINTAINER_NOTES,
          ChangeRequestPermissionAction.READ_CONTRIBUTOR_NOTES, ChangeRequestPermissionAction.WRITE_CONTRIBUTOR_NOTES, ChangeRequestPermissionAction.SUBMIT, ChangeRequestPermissionAction.DELETE));
      
      if (status.equals(AllGovernanceStatus.ACCEPTED))
      {
        actions.remove(ChangeRequestPermissionAction.WRITE_CONTRIBUTOR_NOTES);
        actions.remove(ChangeRequestPermissionAction.WRITE_DETAILS);
        actions.remove(ChangeRequestPermissionAction.DELETE);
      }
      
      return actions;
    }
    
    return new HashSet<ChangeRequestPermissionAction>();
  }
}