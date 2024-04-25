package com.bsva.Jobs.Jobs;

import javax.ejb.EJB;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.bsva.SimpleEjb;

/**
 * @author SalehaR
 *
 */
@DisallowConcurrentExecution
public class BillingExportJob implements Job {
	@EJB
	private SimpleEjb simpleEjb;
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			simpleEjb=new  SimpleEjb();
			try 
			{
				simpleEjb.runBillingExport();
			
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}


