package com.bsva.interfaces;

import javax.ejb.Local;

@Local
public interface QuartzSchedulerBeanLocal {
  public void shutDownScheduler();

  public boolean startAllSchedulers();

  public boolean stopAllSchedulers();

  public boolean rescheduleAllSchedulers(String rescheduleCron, String cronTime);

  public boolean startPain010();

  public boolean startPain012();

  public boolean startPacs002();

  public boolean startST100();

  public boolean startST102();

  public boolean startST104();

  public boolean startBilling();

  public boolean startEotExtract();

  public boolean startDlyReports();

  public boolean reschedulePain010(String rescheduleCron, String cronTime);

  public boolean reschedulePain012(String rescheduleCron, String cronTime);

  public boolean reschedulePacs002(String rescheduleCron, String cronTime);

  public boolean rescheduleST100(String rescheduleCron, String cronTime);

  public boolean rescheduleST102(String rescheduleCron, String cronTime);

  public boolean rescheduleST104(String rescheduleCron, String cronTime);

  public boolean rescheduleBilling(String rescheduleCron, String cronTime);

  public boolean rescheduleEotExtract(String rescheduleCron, String cronTime);

  public boolean rescheduleDlyReports(String rescheduleCron, String cronTime);

  public boolean unschedulePain010();

  public boolean unschedulePain012();

  public boolean unschedulePacs002();

  public boolean unscheduleST100();

  public boolean unscheduleST102();

  public boolean unscheduleST104();

  public boolean unscheduleBilling();

  public boolean unscheduleEotExtract();

  public boolean unscheduleReportGen();

  public boolean updateOpsSchedulerDetails(String schedulerName, String reschTime,
                                           String activeInd);

  public Object getCronValue();
}
