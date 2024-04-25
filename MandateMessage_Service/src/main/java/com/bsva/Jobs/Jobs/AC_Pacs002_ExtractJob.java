package com.bsva.Jobs.Jobs;

import javax.ejb.EJB;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.bsva.SimpleEjb;

@DisallowConcurrentExecution
public class AC_Pacs002_ExtractJob implements Job {
	@EJB
	private SimpleEjb simpleEjb;
	@Override
	public void execute(JobExecutionContext context)	throws JobExecutionException {
	
		simpleEjb = new SimpleEjb();
		try {
			simpleEjb.runACPacs002Extract();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
