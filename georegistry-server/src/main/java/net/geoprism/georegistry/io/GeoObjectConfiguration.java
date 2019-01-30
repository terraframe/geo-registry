package net.geoprism.georegistry.io;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.commongeoregistry.adapter.metadata.AttributeBooleanType;
import org.commongeoregistry.adapter.metadata.AttributeCharacterType;
import org.commongeoregistry.adapter.metadata.AttributeDateType;
import org.commongeoregistry.adapter.metadata.AttributeFloatType;
import org.commongeoregistry.adapter.metadata.AttributeIntegerType;
import org.commongeoregistry.adapter.metadata.AttributeTermType;
import org.commongeoregistry.adapter.metadata.GeoObjectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runwaysdk.dataaccess.MdBusinessDAOIF;
import com.runwaysdk.dataaccess.metadata.MdBusinessDAO;
import com.runwaysdk.session.Request;
import com.runwaysdk.system.gis.geo.Universal;

import net.geoprism.data.importer.BasicColumnFunction;
import net.geoprism.data.importer.ShapefileFunction;
import net.geoprism.georegistry.service.ServiceFactory;
import net.geoprism.localization.LocalizationFacade;

public class GeoObjectConfiguration
{
  public static final String             TARGET        = "target";

  public static String                   TEXT          = "text";

  public static String                   LATITUDE      = "latitude";

  public static String                   LONGITUDE     = "longitude";

  public static String                   NUMERIC       = "numeric";

  public static String                   LONGITUDE_KEY = "georegistry.longitude.label";

  public static String                   LATITUDE_KEY  = "georegistry.latitude.label";

  private Map<String, ShapefileFunction> functions;

  private GeoObjectType                  type;

  private MdBusinessDAOIF                mdBusiness;

  private String                         filename;

  private String                         directory;

  private Map<String, Set<String>>       exclusions;

  private Set<TermProblem>               problems;

  private boolean                        includeCoordinates;

  private List<Location>                 locations;

  private String                         hierarchy;

  public GeoObjectConfiguration()
  {
    this.functions = new HashMap<String, ShapefileFunction>();
    this.problems = new TreeSet<TermProblem>();
    this.locations = new LinkedList<Location>();
    this.includeCoordinates = false;
  }

  public boolean isIncludeCoordinates()
  {
    return includeCoordinates;
  }

  public void setIncludeCoordinates(boolean includeCoordinates)
  {
    this.includeCoordinates = includeCoordinates;
  }

  public GeoObjectType getType()
  {
    return type;
  }

  public void setType(GeoObjectType type)
  {
    this.type = type;
  }

  public MdBusinessDAOIF getMdBusiness()
  {
    return mdBusiness;
  }

  public void setMdBusiness(MdBusinessDAOIF mdBusiness)
  {
    this.mdBusiness = mdBusiness;
  }

  public String getDirectory()
  {
    return directory;
  }

  public void setDirectory(String directory)
  {
    this.directory = directory;
  }

  public String getFilename()
  {
    return filename;
  }

  public void setFilename(String filename)
  {
    this.filename = filename;
  }

  public Map<String, ShapefileFunction> getFunctions()
  {
    return functions;
  }

  public ShapefileFunction getFunction(String attributeName)
  {
    return this.functions.get(attributeName);
  }

  private void setFunction(String attributeName, ShapefileFunction function)
  {
    this.functions.put(attributeName, function);
  }

  public Map<String, Set<String>> getExclusions()
  {
    return exclusions;
  }

  public Set<String> getExclusions(String attributeName)
  {
    return exclusions.get(attributeName);
  }

  public void setExclusions(Map<String, Set<String>> exclusions)
  {
    this.exclusions = exclusions;
  }

  public void addExclusion(String attributeName, String value)
  {
    if (!this.exclusions.containsKey(attributeName))
    {
      this.exclusions.put(attributeName, new TreeSet<String>());
    }

    this.exclusions.get(attributeName).add(value);
  }

  public boolean isExclusion(String attributeName, String value)
  {
    return ( this.exclusions.get(attributeName) != null && this.exclusions.get(attributeName).contains(value) );
  }

  public Set<TermProblem> getProblems()
  {
    return problems;
  }

  public void setProblems(Set<TermProblem> problems)
  {
    this.problems = problems;
  }

  public void addProblem(TermProblem problem)
  {
    this.problems.add(problem);
  }

  public void addParent(Location location)
  {
    this.locations.add(location);
  }

  public List<Location> getLocations()
  {
    return this.locations;
  }

  public String getHierarchy()
  {
    return hierarchy;
  }

  public void setHierarchy(String hierarchy)
  {
    this.hierarchy = hierarchy;
  }

  public JsonObject toJson()
  {
    JsonObject type = this.type.toJSON(new ImportAttributeSerializer(this.includeCoordinates));
    JsonArray attributes = type.get("attributes").getAsJsonArray();

    for (int i = 0; i < attributes.size(); i++)
    {
      JsonObject attribute = attributes.get(i).getAsJsonObject();
      String attributeName = attribute.get("name").getAsString();

      if (this.functions.containsKey(attributeName))
      {
        attribute.addProperty("target", this.functions.get(attributeName).toJson());
      }
    }

    JsonArray locations = new JsonArray();

    for (Location location : this.locations)
    {
      locations.add(location.toJson());
    }

    JsonObject config = new JsonObject();
    config.add("type", type);
    config.add("locations", locations);
    config.addProperty("directory", this.getDirectory());
    config.addProperty("filename", this.getFilename());

    if (this.hierarchy != null)
    {
      config.addProperty("hierarchy", this.getHierarchy());
    }

    if (this.problems.size() > 0)
    {
      JsonArray problems = new JsonArray();

      for (TermProblem problem : this.problems)
      {
        problems.add(problem.toJSON());
      }

      config.add("problems", problems);
    }

    return config;
  }

  @Request
  public static GeoObjectConfiguration parse(String json, boolean includeCoordinates)
  {
    JsonObject config = new JsonParser().parse(json).getAsJsonObject();
    JsonObject type = config.get("type").getAsJsonObject();
    JsonArray locations = config.has("locations") ? config.get("locations").getAsJsonArray() : new JsonArray();
    JsonArray attributes = type.get("attributes").getAsJsonArray();
    String code = type.get("code").getAsString();
    GeoObjectType got = ServiceFactory.getAdapter().getMetadataCache().getGeoObjectType(code).get();
    Universal universal = ServiceFactory.getConversionService().geoObjectTypeToUniversal(got);
    MdBusinessDAOIF mdBusiness = MdBusinessDAO.get(universal.getMdBusinessOid());

    GeoObjectConfiguration configuration = new GeoObjectConfiguration();
    configuration.setDirectory(config.get("directory").getAsString());
    configuration.setFilename(config.get("filename").getAsString());
    configuration.setHierarchy(config.has("hierarchy") ? config.get("hierarchy").getAsString() : null);
    configuration.setType(got);
    configuration.setMdBusiness(mdBusiness);
    configuration.setIncludeCoordinates(includeCoordinates);

    for (int i = 0; i < attributes.size(); i++)
    {
      JsonObject attribute = attributes.get(i).getAsJsonObject();

      if (attribute.has(TARGET))
      {
        String attributeName = attribute.get("name").getAsString();
        String target = attribute.get(TARGET).getAsString();

        configuration.setFunction(attributeName, new BasicColumnFunction(target));
      }
    }

    for (int i = 0; i < locations.size(); i++)
    {
      JsonObject location = locations.get(i).getAsJsonObject();

      if (location.has(TARGET))
      {
        String pCode = location.get("code").getAsString();
        GeoObjectType pType = ServiceFactory.getAdapter().getMetadataCache().getGeoObjectType(pCode).get();
        Universal pUniversal = ServiceFactory.getConversionService().geoObjectTypeToUniversal(pType);

        String target = location.get(TARGET).getAsString();

        configuration.addParent(new Location(pType, pUniversal, new BasicColumnFunction(target)));
      }
    }

    return configuration;
  }

  public static String getBaseType(String attributeType)
  {
    if (attributeType.equals(AttributeBooleanType.TYPE))
    {
      return AttributeBooleanType.TYPE;
    }
    else if (attributeType.equals(AttributeTermType.TYPE) || attributeType.equals(AttributeCharacterType.TYPE))
    {
      return GeoObjectConfiguration.TEXT;
    }
    else if (attributeType.equals(AttributeFloatType.TYPE) || attributeType.equals(AttributeIntegerType.TYPE))
    {
      return GeoObjectConfiguration.NUMERIC;
    }

    return AttributeDateType.TYPE;
  }

  public static String getBaseType(org.opengis.feature.type.AttributeType type)
  {
    Class<?> clazz = type.getBinding();

    if (Boolean.class.isAssignableFrom(clazz))
    {
      return AttributeBooleanType.TYPE;
    }
    else if (String.class.isAssignableFrom(clazz))
    {
      return GeoObjectConfiguration.TEXT;
    }
    else if (Number.class.isAssignableFrom(clazz))
    {
      return GeoObjectConfiguration.NUMERIC;
    }
    else if (Date.class.isAssignableFrom(clazz))
    {
      return AttributeDateType.TYPE;
    }

    throw new UnsupportedOperationException("Unsupported type [" + type.getBinding().getName() + "]");
  }

  public static AttributeFloatType latitude()
  {
    return new AttributeFloatType(GeoObjectConfiguration.LATITUDE, LocalizationFacade.getFromBundles(LATITUDE_KEY), "", false);
  }

  public static AttributeFloatType longitude()
  {
    return new AttributeFloatType(GeoObjectConfiguration.LONGITUDE, LocalizationFacade.getFromBundles(LONGITUDE_KEY), "", false);
  }
}
