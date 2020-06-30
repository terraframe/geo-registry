package net.geoprism.registry.etl;

import org.commongeoregistry.adapter.dataaccess.LocalizedValue;

import net.geoprism.registry.Organization;
import net.geoprism.registry.SynchronizationConfig;
import net.geoprism.registry.conversion.LocalizedValueConverter;
import net.geoprism.registry.graph.ExternalSystem;
import net.geoprism.registry.model.ServerHierarchyType;

public abstract class ExternalSystemSyncConfig
{
  private ExternalSystem      system;

  private Organization        organization;

  private ServerHierarchyType hierarchy;

  private LocalizedValue      label;

  public ExternalSystem getSystem()
  {
    return system;
  }

  public void setSystem(ExternalSystem system)
  {
    this.system = system;
  }

  public Organization getOrganization()
  {
    return organization;
  }

  public void setOrganization(Organization organization)
  {
    this.organization = organization;
  }

  public ServerHierarchyType getHierarchy()
  {
    return hierarchy;
  }

  public void setHierarchy(ServerHierarchyType hierarchy)
  {
    this.hierarchy = hierarchy;
  }

  public LocalizedValue getLabel()
  {
    return label;
  }

  public void setLabel(LocalizedValue label)
  {
    this.label = label;
  }

  public void populate(SynchronizationConfig config)
  {
    this.setHierarchy(config.getServerHierarchyType());
    this.setLabel(LocalizedValueConverter.convert(config.getLabel()));
    this.setOrganization(config.getOrganization());
  }

}