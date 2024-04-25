package com.bsva.businessLogic;

import org.apache.log4j.Logger;
import com.bsva.EJBJMSSender;
import com.bsva.commons.model.ObsStagingModel;

public class BillingLogic 
{

	public  Logger log = Logger.getLogger(BillingLogic.class);

	public void createBillingRecords(ObsStagingModel obsStagingModel) 
	{
		try 
		{
			EJBJMSSender ejbjmsSender = new EJBJMSSender();
//				ejbjmsSender.send(obsStagingModel);			
		} 
		catch (Exception e) 
		{
			log.error("Error on createBillingRecords: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
}
