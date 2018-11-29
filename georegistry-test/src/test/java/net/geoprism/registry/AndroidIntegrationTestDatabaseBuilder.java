package net.geoprism.registry;

import com.runwaysdk.dataaccess.transaction.Transaction;
import com.runwaysdk.session.Request;

import net.geoprism.registry.testframework.USATestData;

/**
 * This class must be run before running the Andorid FullStackIntegrationTest. This class brings your database
 * to a state which will enable the test to run successfully, regardless of any tests that ran before it.
 * 
 * @author rrowlands
 */
public class AndroidIntegrationTestDatabaseBuilder
{
  public static void main(String[] args)
  {
    AndroidIntegrationTestDatabaseBuilder.build();
  }
  
  @Request
  private static void build()
  {
    buildInTransaction();
  }
  @Transaction
  private static void buildInTransaction()
  {
    USATestData data = USATestData.newTestData();
    
    data.newTestGeoObjectInfo("Utah", data.STATE).delete();
    data.newTestGeoObjectInfo("California", data.STATE).delete();
    data.newTestGeoObjectInfo("TEST_ADD_CHILD", data.DISTRICT).apply();
  }
}
