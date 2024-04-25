package com.bsva.interfaces;


import javax.ejb.Remote;

@Remote
public interface FileWatcherRemote {
	public void stopMonitor();
	public void startMonitor();

}
