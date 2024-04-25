package com.bsva.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

@Local
public interface GenericDAOLocal {
	public List findAll(Class clazz);
	public Object find(Class clazz, BigDecimal id);
	public void delete(Object obj);
	public void saveOrUpdate(Object obj);
	public BigDecimal saveReturnId(Object obj);
	public Object findByCriteria(Class clazz, String id);
	public Object find(Class clazz, Short id);
	public Object findByNamedQuery(String namedQuery, String property, String value);
	public List findAllByNamedQuery(String namedQuery, String property, String value);
//	public void save(Object obj);
	public String save(Object obj);
	public void truncateTable(String tableName);
	public List findBySql(String sqlQuery, List<String> scalarList, Class className);
    public List findAllByNamedQuery(String namedQuery, String property, Date value);
    public List findByJoin(Class clazz, String joinedName ,  HashMap<String , Object> parameters);
    public Object findByCriteria(Class clazz , HashMap<String , Object> parameters);
    public List<?> findAllByCriteriaUsingOr(Class clazz , HashMap<String , Object> parameters);
    public List findAllByNamedQuery(String namedQuery, String property, BigDecimal value);
    public List findAllByNQCriteria(String namedQuery , HashMap<String , Object> parameters);
    public List<?> findDistinctByCriteria(Class clazz , HashMap<String , Object> parameters,  String distProperty);
    public List findAllOrdered(Class clazz, String orderByValue);
    public Object findByCriteriaIN(Class clazz , HashMap<String , Object> parameters,String criteria, List inList);
    public List<?> findByCriteriaUsingTrunc(Class clazz , HashMap<String , Object> parameters, String columnName, String dateFormat, String date);
    public List<?> findByCriteriaNotEqual(Class clazz , HashMap<String , Object> parameters);
    public Object findByNamedQueryShort(String namedQuery, String property, Short value);
    public void executeNativeSQL(String sqlQuery);

    //----Testing Methods - not complete-----//
//    public String saveWithFlush(Object obj, int nrWritten);

//	public String saveOrUpdateAll(Collection<Object> objects, int batchSize);
	public void openSession();
	public void closeSession();
	public String optimisedSave(Object obj);
	public boolean findDuplicates(String sqlQuery);
	public String bulkSaveAll(Collection<Object> objects, int batchSize);
	public String bulkUpdateAll(Collection<Object> objects, int batchSize);

}
