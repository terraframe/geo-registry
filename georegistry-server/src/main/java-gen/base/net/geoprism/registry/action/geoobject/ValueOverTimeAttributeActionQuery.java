package net.geoprism.registry.action.geoobject;

@com.runwaysdk.business.ClassSignature(hash = 1747030853)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to ValueOverTimeAttributeAction.java
 *
 * @author Autogenerated by RunwaySDK
 */
public  class ValueOverTimeAttributeActionQuery extends net.geoprism.registry.action.geoobject.AttributeActionQuery

{

  public ValueOverTimeAttributeActionQuery(com.runwaysdk.query.QueryFactory componentQueryFactory)
  {
    super(componentQueryFactory);
    if (this.getComponentQuery() == null)
    {
      com.runwaysdk.business.BusinessQuery businessQuery = componentQueryFactory.businessQuery(this.getClassType());

       this.setBusinessQuery(businessQuery);
    }
  }

  public ValueOverTimeAttributeActionQuery(com.runwaysdk.query.ValueQuery valueQuery)
  {
    super(valueQuery);
    if (this.getComponentQuery() == null)
    {
      com.runwaysdk.business.BusinessQuery businessQuery = new com.runwaysdk.business.BusinessQuery(valueQuery, this.getClassType());

       this.setBusinessQuery(businessQuery);
    }
  }

  public String getClassType()
  {
    return net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.CLASS;
  }
  public com.runwaysdk.query.SelectableChar getAction()
  {
    return getAction(null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias)
  {
    return (com.runwaysdk.query.SelectableChar)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableChar)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewEndDate()
  {
    return getNewEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewStartDate()
  {
    return getNewStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldEndDate()
  {
    return getOldEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldStartDate()
  {
    return getOldStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.getComponentQuery().get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, displayLabel);

  }
  /**  
   * Returns an iterator of Business objects that match the query criteria specified
   * on this query object. 
   * @return iterator of Business objects that match the query criteria specified
   * on this query object.
   */
  public com.runwaysdk.query.OIterator<? extends ValueOverTimeAttributeAction> getIterator()
  {
    this.checkNotUsedInValueQuery();
    String sqlStmt;
    if (_limit != null && _skip != null)
    {
      sqlStmt = this.getComponentQuery().getSQL(_limit, _skip);
    }
    else
    {
      sqlStmt = this.getComponentQuery().getSQL();
    }
    java.util.Map<String, com.runwaysdk.query.ColumnInfo> columnInfoMap = this.getComponentQuery().getColumnInfoMap();

    java.sql.ResultSet results = com.runwaysdk.dataaccess.database.Database.query(sqlStmt);
    return new com.runwaysdk.business.BusinessIterator<ValueOverTimeAttributeAction>(this.getComponentQuery().getMdEntityIF(), columnInfoMap, results);
  }


/**
 * Interface that masks all type unsafe query methods and defines all type safe methods.
 * This type is used when a join is performed on this class as a reference.
 **/
  public interface ValueOverTimeAttributeActionQueryReferenceIF extends net.geoprism.registry.action.geoobject.AttributeActionQuery.AttributeActionQueryReferenceIF
  {

    public com.runwaysdk.query.SelectableChar getAction();
    public com.runwaysdk.query.SelectableChar getAction(String alias);
    public com.runwaysdk.query.SelectableChar getAction(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getNewEndDate();
    public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias);
    public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getNewStartDate();
    public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias);
    public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getOldEndDate();
    public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias);
    public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getOldStartDate();
    public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias);
    public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias, String displayLabel);

    public com.runwaysdk.query.BasicCondition EQ(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction valueOverTimeAttributeAction);

    public com.runwaysdk.query.BasicCondition NE(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction valueOverTimeAttributeAction);

  }

/**
 * Implements type safe query methods.
 * This type is used when a join is performed on this class as a reference.
 **/
  public static class ValueOverTimeAttributeActionQueryReference extends net.geoprism.registry.action.geoobject.AttributeActionQuery.AttributeActionQueryReference
 implements ValueOverTimeAttributeActionQueryReferenceIF

  {

  public ValueOverTimeAttributeActionQueryReference(com.runwaysdk.dataaccess.MdAttributeRefDAOIF mdAttributeIF, String attributeNamespace, String definingTableName, String definingTableAlias, com.runwaysdk.dataaccess.MdBusinessDAOIF referenceMdBusinessIF, String referenceTableAlias, com.runwaysdk.query.ComponentQuery rootQuery, java.util.Set<com.runwaysdk.query.Join> tableJoinSet, String alias, String displayLabel)
  {
    super(mdAttributeIF, attributeNamespace, definingTableName, definingTableAlias, referenceMdBusinessIF, referenceTableAlias, rootQuery, tableJoinSet, alias, displayLabel);

  }


    public com.runwaysdk.query.BasicCondition EQ(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction valueOverTimeAttributeAction)
    {
      if(valueOverTimeAttributeAction == null) return this.EQ((java.lang.String)null);
      return this.EQ(valueOverTimeAttributeAction.getOid());
    }

    public com.runwaysdk.query.BasicCondition NE(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction valueOverTimeAttributeAction)
    {
      if(valueOverTimeAttributeAction == null) return this.NE((java.lang.String)null);
      return this.NE(valueOverTimeAttributeAction.getOid());
    }

  public com.runwaysdk.query.SelectableChar getAction()
  {
    return getAction(null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias)
  {
    return (com.runwaysdk.query.SelectableChar)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableChar)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewEndDate()
  {
    return getNewEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewStartDate()
  {
    return getNewStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldEndDate()
  {
    return getOldEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldStartDate()
  {
    return getOldStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, displayLabel);

  }
  }

/**
 * Interface that masks all type unsafe query methods and defines all type safe methods.
 * This type is used when a join is performed on this class as a reference.
 **/
  public interface ValueOverTimeAttributeActionQueryMultiReferenceIF extends net.geoprism.registry.action.geoobject.AttributeActionQuery.AttributeActionQueryMultiReferenceIF
  {

    public com.runwaysdk.query.SelectableChar getAction();
    public com.runwaysdk.query.SelectableChar getAction(String alias);
    public com.runwaysdk.query.SelectableChar getAction(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getNewEndDate();
    public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias);
    public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getNewStartDate();
    public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias);
    public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getOldEndDate();
    public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias);
    public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias, String displayLabel);
    public com.runwaysdk.query.SelectableMoment getOldStartDate();
    public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias);
    public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias, String displayLabel);

    public com.runwaysdk.query.Condition containsAny(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction);
    public com.runwaysdk.query.Condition notContainsAny(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction);
    public com.runwaysdk.query.Condition containsAll(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction);
    public com.runwaysdk.query.Condition notContainsAll(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction);
    public com.runwaysdk.query.Condition containsExactly(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction);
  }

/**
 * Implements type safe query methods.
 * This type is used when a join is performed on this class as a reference.
 **/
  public static class ValueOverTimeAttributeActionQueryMultiReference extends net.geoprism.registry.action.geoobject.AttributeActionQuery.AttributeActionQueryMultiReference
 implements ValueOverTimeAttributeActionQueryMultiReferenceIF

  {

  public ValueOverTimeAttributeActionQueryMultiReference(com.runwaysdk.dataaccess.MdAttributeMultiReferenceDAOIF mdAttributeIF, String attributeNamespace, String definingTableName, String definingTableAlias, String mdMultiReferenceTableName, com.runwaysdk.dataaccess.MdBusinessDAOIF referenceMdBusinessIF, String referenceTableAlias, com.runwaysdk.query.ComponentQuery rootQuery, java.util.Set<com.runwaysdk.query.Join> tableJoinSet, String alias, String displayLabel)
  {
    super(mdAttributeIF, attributeNamespace, definingTableName, definingTableAlias, mdMultiReferenceTableName, referenceMdBusinessIF, referenceTableAlias, rootQuery, tableJoinSet, alias, displayLabel);

  }



    public com.runwaysdk.query.Condition containsAny(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction)  {

      String[] itemIdArray = new String[valueOverTimeAttributeAction.length]; 

      for (int i=0; i<valueOverTimeAttributeAction.length; i++)
      {
        itemIdArray[i] = valueOverTimeAttributeAction[i].getOid();
      }

      return this.containsAny(itemIdArray);
  }

    public com.runwaysdk.query.Condition notContainsAny(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction)  {

      String[] itemIdArray = new String[valueOverTimeAttributeAction.length]; 

      for (int i=0; i<valueOverTimeAttributeAction.length; i++)
      {
        itemIdArray[i] = valueOverTimeAttributeAction[i].getOid();
      }

      return this.notContainsAny(itemIdArray);
  }

    public com.runwaysdk.query.Condition containsAll(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction)  {

      String[] itemIdArray = new String[valueOverTimeAttributeAction.length]; 

      for (int i=0; i<valueOverTimeAttributeAction.length; i++)
      {
        itemIdArray[i] = valueOverTimeAttributeAction[i].getOid();
      }

      return this.containsAll(itemIdArray);
  }

    public com.runwaysdk.query.Condition notContainsAll(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction)  {

      String[] itemIdArray = new String[valueOverTimeAttributeAction.length]; 

      for (int i=0; i<valueOverTimeAttributeAction.length; i++)
      {
        itemIdArray[i] = valueOverTimeAttributeAction[i].getOid();
      }

      return this.notContainsAll(itemIdArray);
  }

    public com.runwaysdk.query.Condition containsExactly(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction ... valueOverTimeAttributeAction)  {

      String[] itemIdArray = new String[valueOverTimeAttributeAction.length]; 

      for (int i=0; i<valueOverTimeAttributeAction.length; i++)
      {
        itemIdArray[i] = valueOverTimeAttributeAction[i].getOid();
      }

      return this.containsExactly(itemIdArray);
  }
  public com.runwaysdk.query.SelectableChar getAction()
  {
    return getAction(null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias)
  {
    return (com.runwaysdk.query.SelectableChar)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, null);

  }
 
  public com.runwaysdk.query.SelectableChar getAction(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableChar)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.ACTION, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewEndDate()
  {
    return getNewEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getNewStartDate()
  {
    return getNewStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getNewStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.NEWSTARTDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldEndDate()
  {
    return getOldEndDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldEndDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDENDDATE, alias, displayLabel);

  }
  public com.runwaysdk.query.SelectableMoment getOldStartDate()
  {
    return getOldStartDate(null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, null);

  }
 
  public com.runwaysdk.query.SelectableMoment getOldStartDate(String alias, String displayLabel)
  {
    return (com.runwaysdk.query.SelectableMoment)this.get(net.geoprism.registry.action.geoobject.ValueOverTimeAttributeAction.OLDSTARTDATE, alias, displayLabel);

  }
  }
}
