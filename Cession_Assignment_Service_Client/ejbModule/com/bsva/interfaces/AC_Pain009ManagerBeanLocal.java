package com.bsva.interfaces;

import javax.ejb.TransactionManagement;


@TransactionManagement
public interface AC_Pain009ManagerBeanLocal {

	
    public void start();
    public void stop();
	public void destroy();
}
