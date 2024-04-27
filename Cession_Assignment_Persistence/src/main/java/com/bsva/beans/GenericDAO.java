package com.bsva.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;
import com.bsva.entities.CasOpsFileSizeLimitEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.hibernate.GenericDAOLocal;
import com.bsva.hibernate.GenericDAORemote;
import com.bsva.utils.DataAccessLayerException;
import com.bsva.utils.HibernateFactory;

/**
 * Session Bean implementation class GenericDAO
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class GenericDAO implements GenericDAORemote, GenericDAOLocal {

	public Session session;
	public Transaction tx;
	public boolean duplicate;
	public String errorCode = "SALZ WAS HERE!";
	int batchSize = 100;
	//	  private Logger log = Logger.getLogger(GenericDAO.class);

	public void saveOrUpdate(Object obj)
	{
		try {
			startOperation();
			session.saveOrUpdate(obj);
			//	            session.getSessionFactory().getCurrentSession().saveOrUpdate(obj);
			//	            HibernateFactory.getSessionFactory().getCurrentSession().saveOrUpdate(obj);

			tx.commit();
		} 
		catch (HibernateException e) {
			try {
				System.out.println("#####" + e.getMessage() + "####");
				handleException(e);

			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally 
		{
			//	        	System.out.println("session--> "+session);
			//	        	System.out.println("Session is active ? ==> "+ session.isOpen());

			HibernateFactory.close(session);
			//	            System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());

			//	            session.close();
			//	            System.out.println("Session after session.close(); ==> "+ session.isOpen());
		}
	}

	public String save(Object obj)
	{
		try 
		{
			startOperation();
			session.save(obj);
			tx.commit();
			return "SAVED";
		} 
		catch(ConstraintViolationException cve)
		{
			System.out.println("@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("ERROR: "+cve.getMessage());
			System.out.println("SQL Error Code is == "+cve.getErrorCode());
			System.out.println("CVE =="+cve.getConstraintName());

			if(cve.getErrorCode() == 1)
			{
				System.out.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
			}
			//			cve.printStackTrace();
			return "DUPL";
		}
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);

			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
			return "ERROR";
		} 
		finally {
			//            HibernateFactory.close(session);
			//        	System.out.println("session--> "+session);
			//        	System.out.println("Session is active ? ==> "+ session.isOpen());

			HibernateFactory.close(session);
			//            System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());
		}
	}


	//	SalehaR-Test Method
	//	public String saveWithFlush(Object obj, int nrWritten)
	//	{
	//		try 
	//		{
	//			startOperation();
	//			session.save(obj);
	//			tx.commit();
	//			return "SAVED";
	//		} 
	//		catch(ConstraintViolationException cve)
	//		{
	//			System.out.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
	//			System.out.println("ERROR: "+cve.getMessage());
	//			System.out.println("SQL Error Code is == "+cve.getErrorCode());
	//			System.out.println("CVE =="+cve.getConstraintName());
	//
	//			if(cve.getErrorCode() == 1)
	//			{
	//				System.out.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
	//			}
	//			//			cve.printStackTrace();
	//			return "DUPL";
	//		}
	//		catch (HibernateException e) 
	//		{
	//			try 
	//			{
	//				handleException(e);
	//
	//			} 
	//			catch (DataAccessLayerException e1) 
	//			{
	//				e1.printStackTrace();
	//			}
	//			return "ERROR";
	//		} 
	//		finally {
	//			//            HibernateFactory.close(session);
	//			//        	System.out.println("session--> "+session);
	//			//        	System.out.println("Session is active ? ==> "+ session.isOpen());
	//            if (nrWritten % batchSize == 0 && nrWritten > 0) {
	//            	System.out.println("<<<---Flushing Session---->>>");
	//                session.flush();
	//                session.clear();
	//            }
	//
	//			HibernateFactory.close(session);
	//			//            System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());
	//		}
	//}

	public BigDecimal saveReturnId(Object obj) 
	{
		BigDecimal bigId = BigDecimal.ZERO;
		try 
		{
			// System.out.println("In the saveReturnId method ~~~~~~~~~~~~~~~~"+ bigId);
			startOperation();
			//System.out.println("***********saveReturnId---after startoperation----session: "+session+"------tx: "+tx+"**************");
			bigId = (BigDecimal) session.save(obj);
			//System.out.println("bigId: "+ bigId);
			/*session.save(obj);
             id = (Long) session.save(obj);*/
			tx.commit();
		} 
		catch (HibernateException e) 
		{
			System.out.println("Hibernate exception stack :"+e.getMessage());
			e.printStackTrace();
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		finally {
			//     HibernateFactory.close(session);

			HibernateFactory.close(session);
			//        	System.out.println("<<<<<~~~~~SAVE_RETURN_ID~~~~~>>>>>>");
			//            System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());
		}
		return bigId;
	}


	public void delete(Object obj) {
		try {
			startOperation();
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			HibernateFactory.close(session);
		}
	}

	public void truncateTable(String tableName) 
	{
		try 
		{
			startOperation();
			session.createSQLQuery("DELETE FROM "+tableName).executeUpdate();
			tx.commit();
		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally 
		{
			HibernateFactory.close(session);
		}
	}

	public List<?> findAllByCriteria(Class clazz , HashMap<String , Object> parameters){
		List<?> obj = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);

			for (Map.Entry<String , Object> entry : parameters.entrySet()){
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}
			List results = crit.list();

			if (!results.isEmpty()){
				obj = results;
			}
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findAllByCriteria error"+ e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}


	public List findBySql(String sqlQuery, List<String> scalarList, Class className) 
	{
		List objects = null;
		try 
		{
			startOperation();

			SQLQuery sql = session.createSQLQuery(sqlQuery);

			if(scalarList.size() > 0)
			{
				for (String str : scalarList) 
				{
					sql.addScalar(str);
				}
			}
			objects = sql.setResultTransformer(Transformers.aliasToBean(className)).list();
			//System.out.println("*********objects from DAO"+objects.toString());
		} 
		catch (HibernateException e) 
		{
			try 
			{
				System.out.println("#####" + e.getMessage() + "####");
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally 
		{
			HibernateFactory.close(session);
		}
		return objects;
	}


	public Object find(Class clazz, BigDecimal id) {
		Object obj = null;
		try {
			startOperation();
			obj = session.load(clazz, id);
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("#####" + e.getMessage() + "####");
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public Object find(Class clazz, Short id) 
	{
		//	    	System.out.println("In the Generic DAO --FIND()--->");
		Object obj = null;
		try {
			startOperation();
			obj = session.load(clazz, id);
			//	            System.out.println("obj--> "+obj);
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("#####" + e.getMessage() + "####");
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public Object findByCriteria(Class clazz, String id) {
		Object obj = null;
		try {
			startOperation();
			obj = session.load(clazz, id);

			//         tx.commit();
		} catch (HibernateException e) {
			try {

				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("#####" + e.getMessage() + "####");
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public Object findByNamedQuery(String namedQuery, String property, String value)
	{

		Object obj = null;
		try {
			startOperation();
			Query q = session.getNamedQuery(namedQuery);
			if(property != null && !property.isEmpty())
				q.setParameter(property, value);
			obj = q.uniqueResult(); 


		} catch (HibernateException e) {
			try {
				System.out.println("#####" + e.getMessage() + "####");
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public List findAllByNamedQuery(String namedQuery, String property, String value)
	{	
		List objects = null;
		try 
		{
			startOperation();
			Query q = session.getNamedQuery(namedQuery);
			if(!value.equalsIgnoreCase("IGNORE")) //NO where clause therefore cannot pass 'value'
			{
				q.setParameter(property, value);
			}
			objects = (List) q.list();

		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
				System.out.println("#####" + e.getMessage() + "####");
			}
		} 
		finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List findAllByNamedQuery(String namedQuery, String property, Date value)
	{
		List objects = null;
		try 
		{
			startOperation();
			Query q = session.getNamedQuery(namedQuery);
			q.setParameter(property, value);
			objects = (List) q.list();

		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
				System.out.println("#####" + e.getMessage() + "####");
			}
		} 
		finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List findAllByNamedQuery(String namedQuery, String property, BigDecimal value)
	{
		List objects = null;
		try 
		{
			startOperation();
			Query q = session.getNamedQuery(namedQuery);
			q.setParameter(property, value);
			objects = (List) q.list();

		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List groupByNamedQuery(String fiName ){

		List objects=null;

		try{
			startOperation();
			List<GroupedTransactionsDTO> list =	session.createCriteria(CasOpsFileSizeLimitEntity.class).setProjection(Projections.projectionList().add(Projections.groupProperty("fiName"), "collumn").add(Projections.rowCount(), "count"))
					.setResultTransformer(Transformers.aliasToBean(GroupedTransactionsDTO.class))  
					.list();  

			for (GroupedTransactionsDTO groupedTransactionsDTO : list) {
				System.out.println("FI : "  + groupedTransactionsDTO.getCollumn()  + "  Count : "  + groupedTransactionsDTO.getCount());
			}


			//	session.createSQLQuery("select  count (*) from ,group by "+namedQuery);
			//	tx.commit();
		}
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally 
		{
			HibernateFactory.close(session);
		}
		return objects;
	}


	//	    	  //group by and like example
	//	        query = session.createQuery("select e.name, sum(e.salary), count(e)"
	//	                + " from Employee e where e.name like '%i%' group by e.name");
	//	        List<Object[]> groupList = query.list();
	//	        for(Object[] arr : groupList){
	//	            System.out.println(Arrays.toString(arr));
	//	        }
	//			return null;
	//	    	
	//	    	
	//	    	
	//	    }
	public List findAll(Class clazz) {
		List objects = null;
		try {
			startOperation();
			Query query = session.createQuery("from " + clazz.getName());

			objects = query.list();
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List findAllOrdered(Class clazz, String orderByValue) 
	{
		List objects = null;
		try {
			startOperation();
			Query query = session.createQuery("from " + clazz.getName() +" ORDER BY "+orderByValue);

			objects = query.list();
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List findByJoin(Class clazz, String joinedName ,  HashMap<String , Object> parameters)
	{

		List results = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);
			for (Map.Entry<String , Object> entry : parameters.entrySet()){
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));

			}
			crit.setFetchMode(joinedName, FetchMode.JOIN);
			results = crit.list();
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findByJoin error" +e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return results;
	}


	public Object findByCriteria(Class clazz , HashMap<String , Object> parameters)
	{
		Object obj = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);

			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}

			List results = crit.list();

			if (!results.isEmpty()){
				obj = results.get(0);
			}
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				//						e1.printStackTrace();
				System.out.println("GenericDAO findByCriteria error" + e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}


	public Object findByCriteriaIN(Class clazz , HashMap<String , Object> parameters,String criteria, List inList)
	{
		Object obj = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);

			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}

			crit.add(Restrictions.in(criteria, inList));  // for < , <= ,>= , its better to use findByNamedQuery

			List results = crit.list();

			if (!results.isEmpty()){
				obj = results.get(0);
			}
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				//						e1.printStackTrace();
				System.out.println("GenericDAO findByCriteriaIN error" + e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}


	public List<?> findAllByCriteriaUsingOr(Class clazz , HashMap<String , Object> parameters)
	{

		List<?> obj = null;
		try {
			startOperation();

			Criteria crit = session.createCriteria(clazz);
			System.out.println("<==>Crit: "+ crit);

			Disjunction or = Restrictions.disjunction();
			//                  System.out.println("<==>Crit after disjoin: "+ crit);
			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				or.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery

			}
			crit.add(or);
			//                   System.out.println("<==>Crit after restrictions: "+ crit);
			List results = crit.list();

			if (!results.isEmpty()){
				obj = results;
			}
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findAllByCriteria error" + e1);
				System.out.println("#####" + e.getMessage() + "####");
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;


		//	    	System.out.println("---------------In the rfindAllByCriteria method------------------");
		/*   List<?> obj = null;
                   try {
                       startOperation();

                       Criteria crit = session.createCriteria(clazz);

                      System.out.println("<==>Crit: "+ crit);
                       if(useOrStatement)
                		  crit.add(Restrictions.disjunction());

                    System.out.println("<==>Crit after disjoin: "+ crit);
                       for (Map.Entry<String , Object> entry : parameters.entrySet())
                       {
                    	   crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
                       }
                       System.out.println("<==>Crit after restrictions: "+ crit);
                       List results = crit.list();

                       if (!results.isEmpty()){
                         obj = results;
                       }
                       tx.commit();
                   } catch (HibernateException e) {
                       try {
                                      handleException(e);
                               } catch (DataAccessLayerException e1) {
                                      System.out.println("GenericDAO findAllByCriteria error" + e1);
                                      System.out.println("#####" + e.getMessage() + "####");
                               }
                   } finally {
                       HibernateFactory.close(session);
                   }
                   return obj;*/
	}

	public List<?> findDistinctByCriteria(Class clazz , HashMap<String , Object> parameters,  String distProperty)
	{
		//	    	System.out.println("---------------In the rfindAllByCriteria method------------------");
		List<?> obj = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz).addOrder(Order.asc(distProperty));
			//                       System.out.println("<==>Crit: "+ crit);
			crit.setProjection(Projections.distinct(Projections.property(distProperty)));
			//                       System.out.println("<==>Crit after disjoin: "+ crit);
			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}
			//                       System.out.println("<==>Crit after restrictions: "+ crit);
			List results = crit.list();

			if (!results.isEmpty()){
				obj = results;
			}
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findDistinctByCriteria error" + e1);
				System.out.println("#####" + e.getMessage() + "####");
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}


	public List findAllByNQCriteria(String namedQuery , HashMap<String , Object> parameters)
	{

		List objects = new ArrayList<Object>(); 
		try {
			startOperation();
			Query query = session.getNamedQuery(namedQuery);

			for (Map.Entry<String , Object> entry : parameters.entrySet()){ // if you done want to filter, then send an empty hashmap not a null
				query.setParameter(entry.getKey(), entry.getValue());
			}

			List results = query.list();
			if (!results.isEmpty()){
				objects = results;
			}
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findAllByNamedQuery error" +e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}

	public List<?> findAllByOrderCriteria(Class clazz , HashMap<String , Object> parameters,boolean orderBy, String value ){
		List<?> obj = null;
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz).addOrder(Order.asc(value));
			// Criteria crit = session.createCriteria(clazz);

			if(orderBy);
			crit.add(Restrictions.disjunction());

			for (Map.Entry<String , Object> entry : parameters.entrySet()){
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}
			List results = crit.list();

			if (!results.isEmpty()){
				obj = results;
			}
			tx.commit();
		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				System.out.println("GenericDAO findAllByCriteria error"+ e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public List<?> findByCriteriaUsingTrunc(Class clazz , HashMap<String , Object> parameters, String columnName, String dateFormat, String date)
	{
		List objects = new ArrayList<Object>(); 
		try {
			startOperation();
			Criteria crit = session.createCriteria(clazz);

			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}

			crit.add(Restrictions.sqlRestriction("TRUNC("+columnName+") = TO_DATE('" + date + "','"+dateFormat+"')"));

			List results = crit.list();

			if (!results.isEmpty()){
				objects = results;
			}
			tx.commit();

		} catch (HibernateException e) {
			try {
				handleException(e);
			} catch (DataAccessLayerException e1) {
				//						e1.printStackTrace();
				System.out.println("GenericDAO findByCriteriaUsingTrunc error" + e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return objects;
	}


	private void handleException(HibernateException e) throws DataAccessLayerException {
		HibernateFactory.rollback(tx);
		throw new DataAccessLayerException(e);
	}

	public List<?> findByCriteriaNotEqual(Class clazz , HashMap<String , Object> parameters)
	{
		List<?> obj = null;
		try 
		{
			startOperation();
			Criteria crit = session.createCriteria(clazz);

			for (Map.Entry<String , Object> entry : parameters.entrySet())
			{
				crit.add(Restrictions.ne(entry.getKey(), entry.getValue()));  // for < , <= ,>= , its better to use findByNamedQuery
			}

			List results = crit.list();

			if (!results.isEmpty()){
				obj = results;
			}
			tx.commit();

		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				System.out.println("GenericDAO findByCriteriaNotEqual error" + e1);
			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public Object findByNamedQueryShort(String namedQuery, String property, Short value)
	{

		Object obj = null;
		try {
			startOperation();
			Query q = session.getNamedQuery(namedQuery);
			if(property != null && !property.isEmpty())
				q.setParameter(property, value);
			obj = q.uniqueResult(); 


		} catch (HibernateException e) {
			try {
				System.out.println("#####" + e.getMessage() + "####");
				handleException(e);
			} catch (DataAccessLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		} finally {
			HibernateFactory.close(session);
		}
		return obj;
	}

	public void executeNativeSQL(String sqlQuery) 
	{
		try 
		{
			startOperation();
			session.createSQLQuery(sqlQuery).executeUpdate();
			tx.commit();
		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally 
		{
			HibernateFactory.close(session);
		}
	}
	
	public boolean findDuplicates(String sqlQuery) {
		try 
		{
			startOperation();
			Query query = session.createSQLQuery(sqlQuery);
			if(query.list() != null && query.list().size() > 0) {
//				System.out.println("###### Duplicate MRTI: ####### "+ sqlQuery);
				return true;
			}
			else
				return false;
		} 
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
			return false;
		} 
		finally 
		{
			HibernateFactory.close(session);
		}
	}
	

	private void startOperation() throws HibernateException 
	{
		//System.out.println("***********In the startOperation()**************");
		session = HibernateFactory.openSession();
		//System.out.println("++++++++++++session: "+ session);
		tx = session.beginTransaction();
		//System.out.println("@@@@@@@@tx: "+ tx);
		//System.out.println("***********Exiting startOperation()**************");
	}
	
	public void openSession() throws HibernateException 
	{
		HibernateFactory.openSession();
		HibernateFactory.getSessionFactory().getCurrentSession().beginTransaction();
	}
	
	public void closeSession() throws HibernateException{
//		System.out.println("***********In the closeSession()**************");
//		HibernateFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
		HibernateFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
//		HibernateFactory.getSessionFactory().getCurrentSession().flush();
//		HibernateFactory.getSessionFactory().getCurrentSession().clear();
		HibernateFactory.close(HibernateFactory.getSessionFactory().getCurrentSession());
	}
	

	public String optimisedSave(Object obj)
	{
		try 
		{
			//			startOperation();
//			System.out.println("Session ==> "+session);
//			System.out.println("Tx is valid==> "+tx.isActive());
//			System.out.println("Session ==> "+HibernateFactory.getSessionFactory().getCurrentSession());
			HibernateFactory.getSessionFactory().getCurrentSession().save(obj);
//			HibernateFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
			System.out.println("Exiting optimisedSave...");
			return "SAVED";
		} 
		catch(ConstraintViolationException cve)
		{
			System.out.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("ERROR: "+cve.getMessage());
			System.out.println("SQL Error Code is == "+cve.getErrorCode());
			System.out.println("CVE =="+cve.getConstraintName());

			if(cve.getErrorCode() == 1)
			{
				System.out.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
			}
			//			cve.printStackTrace();
			return "DUPL";
		}
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);

			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
			return "ERROR";
		} 
		finally {
			//            HibernateFactory.close(session);
			//        	System.out.println("session--> "+session);
			//        	System.out.println("Session is active ? ==> "+ session.isOpen());

			//			HibernateFactory.close(session);
			//            System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());
		}
	}



	public boolean getDuplicate()
	{
		return duplicate;
	}

	public void setDuplicate(boolean duplicate)
	{
		this.duplicate = duplicate;
	}

	public boolean generateDuplicateError(String msgId, String mrti, String debtorBrNo, String crAbbShrtName, BigDecimal hdrSeqNo)
	{
		boolean saved = false;

		if(hdrSeqNo != null)
		{
			CasOpsStatusDetailsEntity opsStatusDetailsEntity=new CasOpsStatusDetailsEntity();

			opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
			opsStatusDetailsEntity.setStatusHdrSeqNo(hdrSeqNo);
			opsStatusDetailsEntity.setErrorCode("902205");
			opsStatusDetailsEntity.setTxnId(mrti);
			opsStatusDetailsEntity.setTxnStatus("RJCT");
			opsStatusDetailsEntity.setErrorType("TXN");
			opsStatusDetailsEntity.setDebtorBranchNo(debtorBrNo);
			opsStatusDetailsEntity.setCrAbbShortName(crAbbShrtName);

			try 
			{
				save(opsStatusDetailsEntity);
				saved = true;
			} 
			catch (Exception e) 
			{
				System.out.println("Error on generateDuplicateError: " + e.getMessage());
				e.printStackTrace();
				saved = false;
			}
		}
		return saved;
	}


//	Saleha Method	
//	@Override
//	public String saveOrUpdateAll(Collection<Object> objects, int batchSize) {
//		try {
//			System.out.println("%@@@@@@@@@@ RUNNING [[[[BULK INSERT]]]] @@@@@@@@@@%");
//			startOperation();
//			int nrWritten = 0;
//
//			Iterator<Object> iter = objects.iterator();
//			System.out.println("%@@@@@@@@@@ PREPARING TO WRITE [[[["+objects.size()+"]]]] @@@@@@@@@@%");
//			System.out.println("%@@@@@@@@@@ BATCH SIZE ="+batchSize+" %@@@@@@@@@@@@@");
//			while (iter.hasNext()) {
//				session.save(iter.next());
//				nrWritten++;
//
//				if (nrWritten % batchSize == 0 && nrWritten > 0) {
//					System.out.println("@@@@@@@@@@@@@@@@ written to Storeage #["+nrWritten+"] @@@@@@@@@@@@@@@@@@");
//					session.flush();
//					session.clear();
//				}
//			}
//			tx.commit();
//			return "SAVED";
//		} catch (ConstraintViolationException cve) {
//			System.err.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
//			System.err.println("ERROR: " + cve.getMessage());
//			System.err.println("SQL Error Code is == " + cve.getErrorCode());
//			System.err.println("CVE ==" + cve.getConstraintName());
//
//			if (cve.getErrorCode() == 1) {
//				System.err.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
//			}
//			// cve.printStackTrace();
//			return "DUPL";
//		} catch (HibernateException e) {
//			try {
//				handleException(e);
//
//			} catch (DataAccessLayerException e1) {
//				e1.printStackTrace();
//			}
//			return "ERROR";
//		} finally {
//			session.flush();
//			session.clear();
//			HibernateFactory.close(session);
//			// System.out.println("Session after HibernateFactory.close(session); ==> "+
//			// session.isOpen());
//		}
//	}


	

	public String bulkSaveAll(Collection<Object> objects, int batchSize){
		try {
//			startOperation();
//			System.out.println("xxxxxx In the saveOrUpdateAll xxxxx");
			int nrWritten = 0; 

			Iterator<Object> iter = objects.iterator();
			while(iter.hasNext()) {                        
				HibernateFactory.getSessionFactory().getCurrentSession().save(iter.next());               
				nrWritten++;
//				System.out.println("nrWritten ==> "+nrWritten);
				if(nrWritten % batchSize == 0 && nrWritten > 0) {
					HibernateFactory.getSessionFactory().getCurrentSession().flush();
					HibernateFactory.getSessionFactory().getCurrentSession().clear();
				}
			}
			return "SAVED";
		}
		catch(ConstraintViolationException cve)
		{
			System.out.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("ERROR: "+cve.getMessage());
			System.out.println("SQL Error Code is == "+cve.getErrorCode());
			System.out.println("CVE =="+cve.getConstraintName());

			if(cve.getErrorCode() == 1)
			{
				System.out.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
			}
			//			cve.printStackTrace();
			return "DUPL";
		}
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
			return "ERROR";
		} 
		finally {
//			System.out.println("xxxxxx In the finally xxxxx");
			HibernateFactory.getSessionFactory().getCurrentSession().flush();
			HibernateFactory.getSessionFactory().getCurrentSession().clear();
			//System.out.println("Session after HibernateFactory.close(session); ==> "+ session.isOpen());
		} 
	}
	
	@Override
	public String bulkUpdateAll(Collection<Object> objects, int batchSize){
		try {
			int nrWritten = 0; 

			Iterator<Object> iter = objects.iterator();
			while(iter.hasNext()) {                        
				HibernateFactory.getSessionFactory().getCurrentSession().update(iter.next());               
				nrWritten++;
//				System.out.println("nrWritten ==> "+nrWritten);
				if(nrWritten % batchSize == 0 && nrWritten > 0) {
					HibernateFactory.getSessionFactory().getCurrentSession().flush();
					HibernateFactory.getSessionFactory().getCurrentSession().clear();
				}
			}
			return "SAVED";
		}
		catch(ConstraintViolationException cve)
		{
			System.out.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("ERROR: "+cve.getMessage());
			System.out.println("SQL Error Code is == "+cve.getErrorCode());
			System.out.println("CVE =="+cve.getConstraintName());

			if(cve.getErrorCode() == 1)
			{
				System.out.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
			}
			//			cve.printStackTrace();
			return "DUPL";
		}
		catch(NonUniqueObjectException nuoe) {

			System.out.println("XXXXXXXXXXXXXXXXX~~~ Error on bulkUpdateAll<NUOE> ~~~XXXXXXXXXXXXXXXXXXXXX"+nuoe.getMessage());
			return "ERROR";
		}
		catch (HibernateException e) 
		{
			try 
			{
				handleException(e);
			} 
			catch (DataAccessLayerException e1) 
			{
				e1.printStackTrace();
			}
			return "ERROR";
		} 
		finally {
//			System.out.println("xxxxxx In the finally xxxxx");
			HibernateFactory.getSessionFactory().getCurrentSession().flush();
			HibernateFactory.getSessionFactory().getCurrentSession().clear();
		} 
	}
	


//	@Override
//	public String saveOrUpdateAll(Collection<Object> objects, int batchSize) {
//		try {
//			System.out.println("%@@@@@@@@@@ RUNNING [[[[BULK INSERT]]]] @@@@@@@@@@%");
//			startOperation();
//			int nrWritten = 0;
//
//			Iterator<Object> iter = objects.iterator();
//			System.out.println("%@@@@@@@@@@ PREPARING TO WRITE [[[["+objects.size()+"]]]] @@@@@@@@@@%");
//			System.out.println("%@@@@@@@@@@ BATCH SIZE ="+batchSize+" %@@@@@@@@@@@@@");
//			while (iter.hasNext()) {
//				session.save(iter.next());
//				nrWritten++;
//
//				if (nrWritten % batchSize == 0 && nrWritten > 0) {
//					System.out.println("@@@@@@@@@@@@@@@@ written to Storeage #["+nrWritten+"] @@@@@@@@@@@@@@@@@@");
//					session.flush();
//					session.clear();
//				}
//			}
//			tx.commit();
//			return "SAVED";
//		} catch (ConstraintViolationException cve) {
//			System.err.println("%@@@@@@@@@@@Error thrown in the GENERIC DAO@@@@@@@@@@@@@@@@@@@@@@");
//			System.err.println("ERROR: " + cve.getMessage());
//			System.err.println("SQL Error Code is == " + cve.getErrorCode());
//			System.err.println("CVE ==" + cve.getConstraintName());
//
//			if (cve.getErrorCode() == 1) {
//				System.err.println("XXXXXXXXXXXXXXXXX~~~ PK Violation Error~~~XXXXXXXXXXXXXXXXXXXXX");
//			}
//			// cve.printStackTrace();
//			return "DUPL";
//		} catch (HibernateException e) {
//			try {
//				handleException(e);
//
//			} catch (DataAccessLayerException e1) {
//				e1.printStackTrace();
//			}
//			return "ERROR";
//		} finally {
//			session.flush();
//			session.clear();
//			HibernateFactory.close(session);
//			// System.out.println("Session after HibernateFactory.close(session); ==> "+
//			// session.isOpen());
//		}
//	}

}







