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

import com.bsva.file.FileRegMonitorDirectory;
import com.bsva.interfaces.FileRegistryWatcherLocal;
import com.bsva.interfaces.FileRegistryWatcherRemote;

@Startup
@LocalBean
@Singleton
@ConcurrencyManagement
@Lock
public class FileRegistryWatcher implements FileRegistryWatcherRemote, FileRegistryWatcherLocal {

	@EJB
	FileRegistryWatcher fileRegistryWatcher;

	@PostConstruct
	private void postConstruct() {
		fileRegistryWatcher.startMonitor();
	}

	@Asynchronous
	public void stopMonitor() {
		// TODO Auto-generated method stub

	}

	@Asynchronous
	public void startMonitor() {
		try {
			FileRegMonitorDirectory.monitor();
		} catch (ClosedWatchServiceException e) {
			e.printStackTrace();
			System.out.println("error on starting up Monitor : " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error on starting up Monitor : " + e.getMessage());
		}
	}

}
