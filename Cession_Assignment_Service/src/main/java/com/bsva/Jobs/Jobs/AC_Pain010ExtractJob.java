package com.bsva.Jobs.Jobs;

import com.bsva.SimpleEjb;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class AC_Pain010ExtractJob implements Job {
  @EJB
  private SimpleEjb simpleEjb;

  public static Logger log = Logger.getLogger(AC_Pain010ExtractJob.class);
  String mdtLoadType = null;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    simpleEjb = new SimpleEjb();
    simpleEjb.runPain010Extract();
  }

}
