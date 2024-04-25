package com.bsva.utils;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class DataAccessLayerException extends Exception {

	public DataAccessLayerException(HibernateException e) 
	{
		// TODO Auto-generated constructor stub
		System.out.println("Error : "+e.getMessage());
		//System.out.println("e.getCause().getMessage() ==>"+ e.getCause().getMessage());
		
//		SQLException sqlE = ((JDBCException)e).getSQLException();
//		System.out.println("sqlE===>  : "+sqlE);
//		System.out.println("sqlE stck trace===>  : "+sqlE.getStackTrace());
		
		e.printStackTrace();
	}


}
