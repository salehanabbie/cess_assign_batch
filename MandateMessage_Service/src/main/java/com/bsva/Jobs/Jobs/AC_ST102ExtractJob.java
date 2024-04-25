package com.bsva.Jobs.Jobs;

import com.bsva.SimpleEjb;
import javax.ejb.EJB;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
@DisallowConcurrentExecution
public class AC_ST102ExtractJob implements Job {

  @EJB
  private SimpleEjb simpleEjb;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    simpleEjb = new SimpleEjb();
    try {
      simpleEjb.runStatusReportST102Extract();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
