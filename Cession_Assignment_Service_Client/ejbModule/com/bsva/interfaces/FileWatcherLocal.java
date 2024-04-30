package com.bsva.interfaces;

import javax.ejb.Local;

@Local
public interface FileWatcherLocal {

	public void stopMonitor();
	public void startMonitor();
}
