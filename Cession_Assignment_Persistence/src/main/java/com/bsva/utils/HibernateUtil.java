/**
 * 
 */
package com.bsva.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author jeremym
 *
 */
public class HibernateUtil {

	
	private static  SessionFactory sessionFactory = buildSessionFactory();  
	private static ServiceRegistry serviceRegistry;
 
	
	
	 private static SessionFactory buildSessionFactory() {  
	  try {  
	  // return new Configuration().configure().buildSessionFactory();  
		  
			Configuration configuration = new Configuration();
			configuration.addResource("hibernate.cfg.xml");
			configuration.configure();
			serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;

		  
		  
	  } catch (Throwable ex) {  
	   System.err.println("Initial SessionFactory creation failed." + ex);  
	   throw new ExceptionInInitializerError(ex);  
	  }  
	 }  
	  
	 public static SessionFactory getSessionFactory() {  
	  return sessionFactory;  
	 }  
	  
	 public static void shutdown() {  
	  getSessionFactory().close();  
	 }  
	
	
	
	
}
