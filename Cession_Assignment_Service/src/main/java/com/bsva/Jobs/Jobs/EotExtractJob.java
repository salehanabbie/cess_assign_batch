package com.bsva.Jobs.Jobs;

import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.bsva.PropertyUtil;
import com.bsva.SimpleEjb;

@DisallowConcurrentExecution
public class EotExtractJob implements Job {
	
	@EJB
	private SimpleEjb simpleEjb;
	
	@EJB
	PropertyUtil propertyUtil;
	
	public static Logger log = Logger.getLogger(EotExtractJob.class);
	
	String mdtLoadType = null;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try
		{
			propertyUtil = new PropertyUtil();
			mdtLoadType = propertyUtil.getPropValue("MDT.LOAD.TYPE");
		}
		catch (Exception e) {
			log.error("EotExtractJob: Could not find MandateMessageCommons.properties in classpath");
		}
		
		try 
		{
			simpleEjb=new  SimpleEjb();
			simpleEjb.runEotExtract(mdtLoadType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
