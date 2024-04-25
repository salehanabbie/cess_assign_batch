/**
 * 
 */
package com.bsva.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author jeremym
 *
 */


public class HibernateFactory {
    private static SessionFactory sessionFactory;
//    private static Log log = (Log) LogFactory.getLog(HibernateFactory.class);

    /**
     * Constructs a new Singleton SessionFactory
     * @return
     * @throws HibernateException
     */
/*    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }*/

    public static SessionFactory buildSessionFactory() throws HibernateException, DataAccessLayerException {
//        if (sessionFactory != null) {
//            closeFactory();
//        }
//        return configureSessionFactory();
    	return buildIfNeeded();
    }
    
    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    /*public static SessionFactory buildIfNeeded() throws Exception{
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }*/
    
    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    public static SessionFactory buildIfNeeded() throws DataAccessLayerException{
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new DataAccessLayerException(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    

    public static Session openSession() throws HibernateException {
        try {
        	sessionFactory = buildIfNeeded();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
            	ignored.printStackTrace(); 
          //      log.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    public static void close(Session session) {
        if (session != null) 
        {
            try 
            {
                session.close();
            } 
            catch (HibernateException ignored) 
            {
            	System.out.println("Couldn't close Session----> "+ignored.getMessage());
            	ignored.printStackTrace(); 
//                System.out.println("Couldn't close Session", ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
        	ignored.printStackTrace(); 
         //   log.error("Couldn't rollback Transaction", ignored);
        }
    }
    /**
     *
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
/*	        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;*/
    	
    	return HibernateUtil.getSessionFactory();
    	//return HibernateUtil.getSessionFactory();
    }



}