package com.bsva.Jobs.Jobs;

import java.util.Calendar;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bsva.SimpleEjb;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.utils.Util;

public class DailyReportsJob implements Job{
	
	@EJB
	private SimpleEjb simpleEjb;

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		simpleEjb = new SimpleEjb();
		try {
			simpleEjb.runDailyReports();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
