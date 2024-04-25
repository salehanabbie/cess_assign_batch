package com.bsva.interfaces;

import javax.ejb.Remote;

@Remote
public interface TimerBeanRemote {

	public Object cancelTimer(String info);

	public Object extractStart();
	
	public Object startTimer();

}
