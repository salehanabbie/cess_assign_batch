package com.bsva.Jobs.Jobs;

import com.bsva.PropertyUtil;
import com.bsva.SimpleEjb;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//this is the pain 012 Job
@DisallowConcurrentExecution
public class AC_Pain012ExtractJob implements Job {

  @EJB
  private SimpleEjb simpleEjb;

  @EJB
  PropertyUtil propertyUtil;

  public static Logger log = Logger.getLogger(AC_Pain012ExtractJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    simpleEjb = new SimpleEjb();
    simpleEjb.runPain012Extract();

  }
}
