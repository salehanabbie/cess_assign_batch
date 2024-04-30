package com.bsva.interfaces;

import javax.ejb.Local;

@Local
public interface FileRegistryWatcherLocal {
	
	public void stopMonitor();
	public void startMonitor();
}
