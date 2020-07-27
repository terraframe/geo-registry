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
package net.geoprism.registry.etl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.commongeoregistry.adapter.dataaccess.LocalizedValue;
import org.commongeoregistry.adapter.metadata.AttributeBooleanType;
import org.commongeoregistry.adapter.metadata.AttributeDateType;
import org.commongeoregistry.adapter.metadata.AttributeFloatType;
import org.commongeoregistry.adapter.metadata.AttributeIntegerType;
import org.commongeoregistry.adapter.metadata.AttributeTermType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runwaysdk.business.graph.EdgeObject;
import com.runwaysdk.business.graph.GraphQuery;
import com.runwaysdk.dataaccess.MdEdgeDAOIF;
import com.runwaysdk.dataaccess.metadata.graph.MdEdgeDAO;
import com.runwaysdk.session.Request;
import com.runwaysdk.system.scheduler.AllJobStatus;
import com.runwaysdk.system.scheduler.SchedulerManager;

import junit.framework.Assert;
import net.geoprism.registry.Organization;
import net.geoprism.registry.SynchronizationConfig;
import net.geoprism.registry.etl.DHIS2TestService.Dhis2Payload;
import net.geoprism.registry.etl.export.DataExportServiceFactory;
import net.geoprism.registry.etl.export.ExportHistory;
import net.geoprism.registry.etl.export.dhis2.DHIS2GeoObjectJsonAdapters;
import net.geoprism.registry.graph.DHIS2ExternalSystem;
import net.geoprism.registry.graph.ExternalSystem;
import net.geoprism.registry.graph.GeoVertex;
import net.geoprism.registry.model.AttributeTypeMetadata;
import net.geoprism.registry.model.ServerHierarchyType;
import net.geoprism.registry.service.SynchronizationConfigService;
import net.geoprism.registry.test.AllAttributesDataset;
import net.geoprism.registry.test.SchedulerTestUtils;
import net.geoprism.registry.test.TestAttributeTypeInfo;
import net.geoprism.registry.test.TestDataSet;
import net.geoprism.registry.test.TestGeoObjectInfo;
import net.geoprism.registry.test.TestGeoObjectTypeInfo;

public class DHIS2ExportTest
{
  protected static AllAttributesDataset testData;
  
  protected SynchronizationConfigService syncService;
  
  protected ExternalSystem system;
  
  protected DHIS2TestService dhis2;
  
  @BeforeClass
  public static void setUpClass()
  {
    TestDataSet.deleteExternalSystems("DHIS2ExportTest");
    
    testData = AllAttributesDataset.newTestData();
    testData.setUpMetadata();
    
    SchedulerManager.start();
  }

  @AfterClass
  public static void cleanUpClass()
  {
    if (testData != null)
    {
      testData.tearDownMetadata();
    }
    
    SchedulerManager.shutdown();
  }

  @Before
  public void setUp()
  {
    if (testData != null)
    {
      testData.setUpInstanceData();
    }
    
    this.dhis2 = new DHIS2TestService();
    DataExportServiceFactory.setDhis2Service(this.dhis2);
    
    system = createDhis2ExternalSystem();
    
    syncService = new SynchronizationConfigService();
    
    
    
//    deleteExternalIds();
    
    
//    createExternalIds();
  }
  
  @After
  public void tearDown()
  {
    if (testData != null)
    {
      testData.tearDownInstanceData();
    }
    
//    deleteExternalIds();
    TestDataSet.deleteExternalSystems("DHIS2ExportTest");
  }
  
  @Request
  private void createExternalIds()
  {
    createExternalIdsInTrans();
  }
//  @Transaction
  private void createExternalIdsInTrans()
  {
    for (TestGeoObjectInfo go : testData.getManagedGeoObjects())
    {
      go.getServerObject().createExternalId(this.system, "ManualIdCreateTest");
    }
  }
  
  @Request
  private void deleteExternalIds()
  {
    final MdEdgeDAOIF mdEdge = MdEdgeDAO.getMdEdgeDAO(GeoVertex.EXTERNAL_ID);

    StringBuilder builder = new StringBuilder();
    builder.append("SELECT FROM " + mdEdge.getDBClassName());

    builder.append(" WHERE out = :system");
    
    final GraphQuery<EdgeObject> query = new GraphQuery<EdgeObject>(builder.toString());
    
    query.setParameter("system", this.system.getRID());

    List<EdgeObject> edges = query.getResults();
    
    for (EdgeObject edge : edges)
    {
      edge.delete();
    }
  }
  
  @Request
  private ExternalSystem createDhis2ExternalSystem()
  {
    DHIS2ExternalSystem system = new DHIS2ExternalSystem();
    system.setId("DHIS2ExportTest");
    system.setOrganization(testData.ORG.getServerObject());
    system.getEmbeddedComponent(ExternalSystem.LABEL).setValue("defaultLocale", "Test");
    system.getEmbeddedComponent(ExternalSystem.DESCRIPTION).setValue("defaultLocale", "Test");
    system.setUsername("mock");
    system.setPassword("mock");
    system.setUrl("mock");
    system.setVersion("2.31.9");
    system.apply();
    
    return system;
  }
  
  @Request
  private SynchronizationConfig createSyncConfig(SyncLevel additionalLevel)
  {
    // Define reusable objects
    final ServerHierarchyType ht = testData.HIER.getServerObject();
    final Organization org = testData.ORG.getServerObject();
    
    // Create DHIS2 Sync Config
    DHIS2SyncConfig dhis2Config = new DHIS2SyncConfig();
    dhis2Config.setHierarchy(ht);
    dhis2Config.setLabel(new LocalizedValue("DHIS2 Export Test Data"));
    dhis2Config.setOrganization(org);
    
    // Populate Levels
    SortedSet<SyncLevel> levels = new TreeSet<SyncLevel>();
    
    SyncLevel level = new SyncLevel();
    level.setGeoObjectType(testData.GOT_ALL.getServerObject().getCode());
    level.setSyncType(SyncLevel.Type.ALL);
    level.setLevel(1);
    levels.add(level);
    
    // Populate Attribute Mappings
    if (additionalLevel != null)
    {
      levels.add(additionalLevel);
    }
    
    dhis2Config.setLevels(levels);
    
    // Serialize the DHIS2 Config
    GsonBuilder builder = new GsonBuilder();
    String dhis2JsonConfig = builder.create().toJson(dhis2Config);
    
    // Create a SynchronizationConfig
    SynchronizationConfig config = new SynchronizationConfig();
    config.setConfiguration(dhis2JsonConfig);
    config.setOrganization(org);
    config.setHierarchy(ht.getEntityRelationship());
    config.setSystem(this.system.getOid());
    config.getLabel().setValue("DHIS2 Export Test");
    config.apply();
    
    return config;
  }
  
  private void exportCustomAttribute(TestGeoObjectTypeInfo got, TestGeoObjectInfo go, TestAttributeTypeInfo attr) throws InterruptedException
  {
    SyncLevel level2 = new SyncLevel();
    level2.setGeoObjectType(got.getServerObject().getCode());
    level2.setSyncType(SyncLevel.Type.ALL);
    level2.setLevel(2);
    
    Map<String, DHIS2AttributeMapping> mappings = new HashMap<String, DHIS2AttributeMapping>();
    
    DHIS2AttributeMapping mapping = new DHIS2AttributeMapping();
    
    mapping.setName(attr.getAttributeName());
    mapping.setExternalId("TEST_EXTERNAL_ID");
    mappings.put(attr.getAttributeName(), mapping);
    
    level2.setAttributes(mappings);
    
    Map<String, String> terms = new HashMap<String, String>();
    terms.put(TestDataSet.getClassifierIfExist(AllAttributesDataset.TERM_TERM_ROOT.getCode()).getOid(), "TEST_EXTERNAL_ID");
    terms.put(TestDataSet.getClassifierIfExist(AllAttributesDataset.TERM_TERM_VAL1.getCode()).getOid(), "TEST_EXTERNAL_ID");
    terms.put(TestDataSet.getClassifierIfExist(AllAttributesDataset.TERM_TERM_VAL2.getCode()).getOid(), "TEST_EXTERNAL_ID");
    mapping.setTerms(terms);
    
    SynchronizationConfig config = createSyncConfig(level2);
    
    JsonObject jo = syncService.run(testData.adminSession.getSessionId(), config.getOid());
    ExportHistory hist = ExportHistory.get(jo.get("historyId").getAsString());
    
    SchedulerTestUtils.waitUntilStatus(hist, AllJobStatus.SUCCESS);
    
    LinkedList<Dhis2Payload> payloads = this.dhis2.getPayloads();
    Assert.assertEquals(2, payloads.size());
    
    for (int level = 0; level < payloads.size(); ++level)
    {
      Dhis2Payload payload = payloads.get(level);
      
      JsonObject joPayload = JsonParser.parseString(payload.getData()).getAsJsonObject();
      
      JsonArray orgUnits = joPayload.get("organisationUnits").getAsJsonArray();
      
      Assert.assertEquals(1, orgUnits.size());
      
      JsonObject orgUnit = orgUnits.get(0).getAsJsonObject();
      
      Assert.assertEquals(level + 1, orgUnit.get("level").getAsInt());
      
      Assert.assertEquals("MULTI_POLYGON", orgUnit.get("featureType").getAsString());
      
      if (level == 0)
      {
        Assert.assertEquals(testData.GO_ALL.getCode(), orgUnit.get("code").getAsString());
      }
      else
      {
        Assert.assertEquals(go.getCode(), orgUnit.get("code").getAsString());
        
        Assert.assertTrue(orgUnit.has("attributeValues"));
        
        JsonArray attributeValues = orgUnit.get("attributeValues").getAsJsonArray();
        
        Assert.assertEquals(1, attributeValues.size());
        
        JsonObject attributeValue = attributeValues.get(0).getAsJsonObject();
        
        Assert.assertNotNull(attributeValue.get("lastUpdated").getAsString());
        
        Assert.assertNotNull(attributeValue.get("created").getAsString());
        
        if (attr.toDTO() instanceof AttributeIntegerType)
        {
          Assert.assertEquals(go.getServerObject().getValue(attr.getAttributeName()), attributeValue.get("value").getAsLong());
        }
        else if (attr.toDTO() instanceof AttributeFloatType)
        {
          Assert.assertEquals(go.getServerObject().getValue(attr.getAttributeName()), attributeValue.get("value").getAsDouble());
        }
        else if (attr.toDTO() instanceof AttributeDateType)
        {
          // TODO : If we fetch the object from the database in this manner the miliseconds aren't included on the date. But if we fetch the object via a query (as in DataExportJob) then the miliseconds ARE included...
//          String expected = DHIS2GeoObjectJsonAdapters.DHIS2Serializer.formatDate((Date) go.getServerObject().getValue(attr.getAttributeName()));
          
          String expected = DHIS2GeoObjectJsonAdapters.DHIS2Serializer.formatDate(AllAttributesDataset.GO_DATE_VALUE);
          String actual = attributeValue.get("value").getAsString();
          
          Assert.assertEquals(expected, actual);
        }
        else if (attr.toDTO() instanceof AttributeBooleanType)
        {
          Assert.assertEquals(go.getServerObject().getValue(attr.getAttributeName()), attributeValue.get("value").getAsBoolean());
        }
        else if (attr.toDTO() instanceof AttributeTermType)
        {
          String dhis2Id = attributeValue.get("value").getAsString();
          
          // Term term = (Term) go.getServerObject().getValue(attr.getAttributeName());
          
          Assert.assertEquals("TEST_EXTERNAL_ID", dhis2Id);
        }
        else
        {
          Assert.assertEquals(go.getServerObject().getValue(attr.getAttributeName()), attributeValue.get("value").getAsString());
        }
        
        Assert.assertEquals("TEST_EXTERNAL_ID", attributeValue.get("attribute").getAsJsonObject().get("id").getAsString());
      }
    }
  }
  
//  @Test
//  @Request
//  public void testSerializeConfig()
//  {
//    SyncLevel level2 = new SyncLevel();
//    level2.setGeoObjectType(testData.GOT_CHAR.getServerObject().getCode());
//    level2.setSyncType(SyncLevel.Type.ALL);
//    level2.setLevel(2);
//    
//    SynchronizationConfig config = createSyncConfig(level2);
//    
//    JsonObject json = config.toJSON();
//    
//    JsonObject dhis2Config = json.get(SynchronizationConfig.CONFIGURATION).getAsJsonObject();
//    
//    JsonArray levels = dhis2Config.get("levels").getAsJsonArray();
//    
//    Assert.assertEquals(2, levels.size());
//    
//    JsonObject level = levels.get(1).getAsJsonObject();
//    
//    JsonObject attributes = level.get("attributes").getAsJsonObject();
//    
//    Assert.assertEquals(1, attributes.size());
//  }
  
  @Test
  @Request
  public void testExportCharacterAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_CHAR, testData.GO_CHAR, testData.AT_GO_CHAR);
  }
  
  @Test
  @Request
  public void testExportIntegerAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_INT, testData.GO_INT, testData.AT_GO_INT);
  }
  
  @Test
  @Request
  public void testExportFloatAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_FLOAT, testData.GO_FLOAT, testData.AT_GO_FLOAT);
  }
  
  @Test
  @Request
  public void testExportDateAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_DATE, testData.GO_DATE, testData.AT_GO_DATE);
  }
  
  @Test
  @Request
  public void testExportBoolAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_BOOL, testData.GO_BOOL, testData.AT_GO_BOOL);
  }
  
  @Test
  @Request
  public void testExportTermAttr() throws Exception
  {
    exportCustomAttribute(testData.GOT_TERM, testData.GO_TERM, testData.AT_GO_TERM);
  }
  
  @Test
  @Request
  public void testGetCustomAttributeConfiguration() throws Exception
  {
    SynchronizationConfig config = createSyncConfig(null);
    
    JsonArray custConfig = this.syncService.getCustomAttributeConfiguration(testData.adminSession.getSessionId(), config.getSystem(), testData.GOT_ALL.getCode());
    
    System.out.println(custConfig.toString());
    
    for (int i = 0; i < custConfig.size(); ++i)
    {
      JsonObject attr = custConfig.get(i).getAsJsonObject();
      
      String name = attr.get("name").getAsString();
      
      TestAttributeTypeInfo attrType = null;
      
      JsonArray dhis2Attrs = attr.get("dhis2Attrs").getAsJsonArray();
      
      if (name.equals(testData.AT_ALL_INT.getAttributeName()))
      {
        attrType = testData.AT_ALL_INT;
        
        Assert.assertEquals(1, dhis2Attrs.size());
        
        JsonObject dhis2Attr = dhis2Attrs.get(0).getAsJsonObject();
        
        Assert.assertEquals("V9JL0MAFQop", dhis2Attr.get("dhis2Id").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Integer", dhis2Attr.get("code").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Integer", dhis2Attr.get("name").getAsString());
      }
      else if (name.equals(testData.AT_ALL_BOOL.getAttributeName()))
      {
        attrType = testData.AT_ALL_BOOL;
        
        Assert.assertEquals(1, dhis2Attrs.size());
        
        JsonObject dhis2Attr = dhis2Attrs.get(0).getAsJsonObject();
        
        Assert.assertEquals("HoRXtod7Z8W", dhis2Attr.get("dhis2Id").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Bool", dhis2Attr.get("code").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Bool", dhis2Attr.get("name").getAsString());
      }
      else if (name.equals(testData.AT_ALL_CHAR.getAttributeName()))
      {
        attrType = testData.AT_ALL_CHAR;
        
        Assert.assertEquals(3, dhis2Attrs.size());
        
        for (int j = 0; j < dhis2Attrs.size(); j++)
        {
          JsonObject dhis2Attr = dhis2Attrs.get(j).getAsJsonObject();
          
          String id = dhis2Attr.get("dhis2Id").getAsString();
          Assert.assertTrue(id.equals("UKNKz1H10EE") || id.equals("n2xYlNbsfko") || id.equals("tw1zAoX9tP6"));
          
          String code = dhis2Attr.get("code").getAsString();
          Assert.assertTrue(code.equals("IRID") || code.equals("NGOID") || code.equals("CGRIntegrationAttributeTest-Char"));
        }
      }
      else if (name.equals(testData.AT_ALL_DATE.getAttributeName()))
      {
        attrType = testData.AT_ALL_DATE;
        
        Assert.assertEquals(1, dhis2Attrs.size());
        
        JsonObject dhis2Attr = dhis2Attrs.get(0).getAsJsonObject();
        
        Assert.assertEquals("Z5TiJm1H4TC", dhis2Attr.get("dhis2Id").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Date", dhis2Attr.get("code").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Date", dhis2Attr.get("name").getAsString());
      }
      else if (name.equals(testData.AT_ALL_FLOAT.getAttributeName()))
      {
        attrType = testData.AT_ALL_FLOAT;
        
        Assert.assertEquals(1, dhis2Attrs.size());
        
        JsonObject dhis2Attr = dhis2Attrs.get(0).getAsJsonObject();
        
        Assert.assertEquals("Po0hdj4UHUv", dhis2Attr.get("dhis2Id").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Float", dhis2Attr.get("code").getAsString());
        
        Assert.assertEquals("CGRIntegrationAttributeTest-Float", dhis2Attr.get("name").getAsString());
      }
      else if (name.equals(testData.AT_ALL_TERM.getAttributeName()))
      {
        attrType = testData.AT_ALL_TERM;
        
        Assert.assertEquals(2, dhis2Attrs.size());
        
        for (int j = 0; j < dhis2Attrs.size(); j++)
        {
          JsonObject dhis2Attr = dhis2Attrs.get(j).getAsJsonObject();
          
          String id = dhis2Attr.get("dhis2Id").getAsString();
          
          JsonArray options = dhis2Attr.get("options").getAsJsonArray();
          
          if (id.equals("Wt2PuMK4kTt"))
          {
            Assert.assertEquals(4, options.size());
            
            JsonObject option = options.get(0).getAsJsonObject();
            
            Assert.assertNotNull(option.get("code").getAsString());
            
            Assert.assertNotNull(option.get("name").getAsString());
            
            Assert.assertNotNull(option.get("id").getAsString());
          }
          else if (id.equals("Bp9g0VvC1fK"))
          {
            Assert.assertEquals(2, options.size());
            
            JsonObject option = options.get(0).getAsJsonObject();
            
            String code = option.get("code").getAsString();
            
            Assert.assertTrue(code.equals("0-14 years") || code.equals("val2"));
            
            String optionName = option.get("name").getAsString();
            
            Assert.assertTrue(optionName.equals("val1") || optionName.equals("val2"));
            
            String optionId = option.get("id").getAsString();
            
            Assert.assertTrue(optionId.equals("val1") || optionId.equals("val2"));
          }
          else
          {
            Assert.fail("Unexpected id [" + id + "].");
          }
        }
        
        JsonArray cgrTerms = attr.get("terms").getAsJsonArray();
        
        Assert.assertEquals(2, cgrTerms.size());
        
        for (int k = 0; k < cgrTerms.size(); ++k)
        {
          JsonObject term = cgrTerms.get(k).getAsJsonObject();
          
          String label = term.get("label").getAsString();
          Assert.assertTrue(AllAttributesDataset.TERM_ALL_VAL1.getLabel().getValue().equals(label) || AllAttributesDataset.TERM_ALL_VAL2.getLabel().getValue().equals(label));
          
          String code = term.get("code").getAsString();
          Assert.assertTrue(AllAttributesDataset.TERM_ALL_VAL1.getCode().equals(code) || AllAttributesDataset.TERM_ALL_VAL2.getCode().equals(code));
        }
      }
      else
      {
        Assert.fail("Unexpected attribute name [" + name + "].");
      }
      
      Assert.assertEquals(attrType.toDTO().getLabel().getValue(), attr.get("label").getAsString());
      
      Assert.assertEquals(attrType.toDTO().getType(), attr.get("type").getAsString());
      
      Assert.assertNotNull(attr.get("typeLabel").getAsString());
      Assert.assertEquals(AttributeTypeMetadata.get().getTypeEnumDisplayLabel(attrType.toDTO().getType()), attr.get("typeLabel").getAsString());
    }
  }
}