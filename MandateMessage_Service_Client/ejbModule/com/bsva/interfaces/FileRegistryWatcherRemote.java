package com.bsva.interfaces;

import javax.ejb.Remote;

@Remote
public interface FileRegistryWatcherRemote {

	public void stopMonitor();
	public void startMonitor();
}
