package com.bsva;

import java.nio.file.ClosedWatchServiceException;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.bsva.file.MonitorDirectory;
import com.bsva.interfaces.FileWatcherLocal;
import com.bsva.interfaces.FileWatcherRemote;
import com.bsva.interfaces.ServiceBeanRemote;

/**
 * Session Bean implementation class FileWatcher
 */
@Startup
@LocalBean
@Singleton
@ConcurrencyManagement
@Lock
public class FileWatcher  implements FileWatcherRemote, FileWatcherLocal {

	@EJB
	private FileWatcher me;
	
	@EJB
	ServiceBeanRemote beanRemote;

	@PostConstruct
	private void postConstruct() {
		me.startMonitor();
	}

	@Asynchronous
	public void startMonitor() 
	{
		try
		{
			MonitorDirectory.monitor();
		}
		catch (ClosedWatchServiceException e )
		{
			e.printStackTrace();
			System.out.println("error on starting up Monitor : "+e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error on starting up Monitor : "+e.getMessage());
		}
	}

	@Asynchronous
	public void stopMonitor()
	{
		MonitorDirectory.stopWatch();	
	}
	

	/*public FileWatcher() throws JMSException, NamingException {

			MonitorDirectory.monitor(); 
	}*/
	
}

