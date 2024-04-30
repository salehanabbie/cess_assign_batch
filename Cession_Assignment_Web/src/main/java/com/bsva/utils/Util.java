package com.bsva.utils;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;



import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileWatcherRemote;
import com.bsva.interfaces.JobSchedulerBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.TimerBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;

public class Util {

	private static Context ic;
	public static Logger log = Logger.getLogger(Util.class);


	public static ServiceBeanRemote getServiceBean() {

		if (ic == null) {
			setContext();
		}



		try {
	/*		Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;
		*/
			return (ServiceBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");

			//return (ServiceBeanRemote) ic.lookup("java:jboss/datasources/MandateDS");

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Error 1");
		}
		return null;

	}

	private static void setContext() {

		try {
			// Get the Initial Context for the JNDI lookup for a local EJB
			final Hashtable jndiProperties = new Hashtable();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

			ic = new InitialContext(jndiProperties);

		} catch (NamingException e) {
			e.printStackTrace();
			e.getMessage();
		}


	}

	public static AdminBeanRemote getAdminBean() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
	/*		Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;
		*/

			return (AdminBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/AdminBean!com.bsva.interfaces.AdminBeanRemote");
		}
		catch (Exception e)
		{
			log.debug("Error on getAdminBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static TimerBeanRemote getTimerBean() {

		if (ic == null)
		{
			setContext();
		}
		try
		{
	/*		Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;
		*/

			return (TimerBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/TimerBean!com.bsva.interfaces.TimerBeanRemote");
		}
		catch (Exception e)
		{
			log.debug("Error on getTimerBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static ValidationBeanRemote getValidationBean() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
	/*		Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;
		*/

			return (ValidationBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/ValidationBean!com.bsva.interfaces.ValidationBeanRemote");

		}
		catch (Exception e)
		{
			log.debug("Error on getValidationBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static QuartzSchedulerBeanRemote getQuartzSchedulerBean() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
			/*Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;   */


			return (QuartzSchedulerBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/QuartzSchedulerBean!com.bsva.interfaces.QuartzSchedulerBeanRemote");

		}
		catch (Exception e)
		{
			log.debug("Error on getQuartzSchedulerBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}


	public static FileWatcherRemote  getFileWatcherBean() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
			/*Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;   */
			return (FileWatcherRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/FileWatcher!com.bsva.interfaces.FileWatcherRemote");

		}
		catch (Exception e)
		{
			log.debug("Error on getFileWatcherBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static PropertyUtilRemote getPropertyUtil() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
			return (PropertyUtilRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/PropertyUtil!com.bsva.interfaces.PropertyUtilRemote");

		}
		catch (Exception e)
		{
			log.debug("Error on getPropertyUtil(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static ReportBeanRemote getReportBean() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
			return (ReportBeanRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/ReportBean!com.bsva.interfaces.ReportBeanRemote");
		}
		catch (Exception e)
		{
			log.debug("Error on getReportBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}


	/*public static CisServiceRemote getCisServiceRemote() {
		if (ic == null)
		{
			setContext();
		}
		try
		{
			Object object =
					ic.lookup("java:global/Mandate_Service_Layer-0.0.1-SNAPSHOT/Service-0.0.1-SNAPSHOT/ServiceBean!com.bsva.interfaces.ServiceBeanRemote");
			ServiceBeanRemote beanRemote = (ServiceBeanRemote)object;


//			return (CisServiceRemote) ic.lookup("java:global/Cession_Assignment_Service_Common-0.0.1-SNAPSHOT/Cession_Assignment_Service-0.0.1-SNAPSHOT/CisServiceBean!com.bsva.cis.ejb.interfaces.CisServiceRemote");
			return (CisServiceRemote) ic.lookup("java:global/BSVA_CIS_Web-0.0.1-SNAPSHOT/CisServiceBean!com.bsva.cis.ejb.interfaces.CisServiceRemote");

		}
		catch (Exception e)
		{
			log.debug("Error on getCISServiceBean(): "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	*/
	/*public static <T> T connectEJB(String jndi) throws NamingException {
	    Properties clientProperties = new Properties();
	    clientProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
	    clientProperties.put("remote.connections", "default");
	    clientProperties.put("remote.connection.default.port", myPort);
	    clientProperties.put("remote.connection.default.host", myHost);
	    clientProperties.put("remote.connection.default.username", myUser);
	    clientProperties.put("remote.connection.default.password", myPassword);
	    clientProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

	    EJBClientConfiguration ejbClientConfiguration = new PropertiesBasedEJBClientConfiguration(clientProperties);
	    ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(ejbClientConfiguration);
	    EJBClientContext.setSelector(contextSelector);

	    Properties properties = new Properties();
	    properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	    Context context = new InitialContext(properties);
	    return (T) context.lookup(jndi);
	}*/
}
