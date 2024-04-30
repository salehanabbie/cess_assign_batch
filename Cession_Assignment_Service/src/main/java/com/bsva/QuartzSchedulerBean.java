package com.bsva;

import com.bsva.Jobs.Jobs.AC_Pacs002_ExtractJob;
import com.bsva.Jobs.Jobs.AC_Pain010ExtractJob;
import com.bsva.Jobs.Jobs.AC_Pain012ExtractJob;
import com.bsva.Jobs.Jobs.AC_ST200ExtractJob;
import com.bsva.Jobs.Jobs.AC_ST202ExtractJob;
import com.bsva.Jobs.Jobs.AC_ST204ExtractJob;
import com.bsva.Jobs.Jobs.BillingExportJob;
import com.bsva.Jobs.Jobs.DailyReportsJob;
import com.bsva.Jobs.Jobs.EotExtractJob;
import com.bsva.Jobs.Jobs.StartofDayJob;
import com.bsva.beans.GenericDAO;
import com.bsva.commons.model.SchedulerCronModel;
import com.bsva.entities.CasOpsSchedulerEntity;
import com.bsva.entities.CasSysctrlSchedulerCronEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.QuartzSchedulerBeanLocal;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.translator.AdminTranslator;
import com.bsva.utils.Util;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.hibernate.ObjectNotFoundException;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class QuartzSchedulerBean implements QuartzSchedulerBeanRemote, QuartzSchedulerBeanLocal {
  /**
   * @author ElelwaniR
   */
  @EJB
  GenericDAO genericDAO;

  private Logger log = LoggerFactory.getLogger(QuartzSchedulerBean.class);
  private Scheduler scheduler;
  private static AdminBeanRemote adminBeanRemote;
  boolean unschedulePain010 = false, unshedulePain009 = false, unschedulePain011 = false,
      unscheduleEndofDay = false, unschedulePacs = false, unschedulePain012 = false;
  private String systemName = "MANOWNER";

  @Inject
  private JobFactory cdiJobFactory;
  Date dateSod = new Date();
  Date runTime = DateBuilder.evenMinuteDate(new Date());
  //Job keys declarations
  JobKey job4Key = JobKey.jobKey("Pain010ExtractJob", "my-jobs");
  JobKey job6Key = JobKey.jobKey("Pain012ExtractJob", "my-jobs");
  JobKey job8Key = JobKey.jobKey("StartofDayJob", "my-jobs");
  JobKey job10Key = JobKey.jobKey("Pacs002ExtractJob", "my-jobs");
  JobKey jobKey11 = JobKey.jobKey("ST100Job", "my-jobs");
  JobKey jobKey12 = JobKey.jobKey("ST102Job", "my-jobs");
  JobKey jobKey13 = JobKey.jobKey("ST104Job", "my-jobs");
  JobKey jobKey22 = JobKey.jobKey("BILLJob", "my-jobs");
  JobKey jobKey23 = JobKey.jobKey("EOTJob", "my-jobs");
  JobKey jobKey26 = JobKey.jobKey("DlyReportsJob", "my-jobs");

  /// Job triggers for all the extracts
  TriggerKey pain010TK = TriggerKey.triggerKey("Pain010Trigger", "my-jobs");
  TriggerKey pain012TK = TriggerKey.triggerKey("Pain012Trigger", "my-jobs");
  TriggerKey startofdayTK = TriggerKey.triggerKey("StartofDayTrigger", "my-jobs");
  TriggerKey pacs002TK = TriggerKey.triggerKey("Pacs002ExtTrigger", "my-jobs");
  TriggerKey st100TK = TriggerKey.triggerKey("ST100Trigger", "my-jobs");
  TriggerKey st102TK = TriggerKey.triggerKey("ST102Trigger", "my-jobs");
  TriggerKey st104TK = TriggerKey.triggerKey("ST104Trigger", "my-jobs");
  TriggerKey billTK = TriggerKey.triggerKey("BILLTrigger", "my-jobs");
  TriggerKey eotTK = TriggerKey.triggerKey("EOTTrigger", "my-jobs");
  TriggerKey reportTK = TriggerKey.triggerKey("reportTrigger", "my-jobs");

  JobDetail pain010Job;
  JobDetail pain012Job;
  JobDetail startOfDayJob;
  JobDetail pacs002ExtJob;
  JobDetail st100Job;
  JobDetail st102Job;
  JobDetail st104Job;
  JobDetail billJob;
  JobDetail eotJob;
  JobDetail reportJob;

  Trigger pain010Trigger;
  Trigger pain012Trigger;
  Trigger startOfDayTrigger;
  Trigger pacs002Trigger;
  Trigger st100Trigger;
  Trigger st102Trigger;
  Trigger st104Trigger;
  Trigger billTrigger;
  Trigger eotTrigger;
  Trigger reportTrigger;

  Date triggerStartTime = new Date();
  int seconds = 0, minutes = 15;

  //	2020/05/28-SALEHAR: Change Cron String to run reports scheduler daily
//	String cronValue = "1 15 4 ? * SUN";
  String cronValue = "0 15 4 * * ?";


  public QuartzSchedulerBean() {
    contextAdminBeanCheck();
  }

  @PostConstruct
  public void scheduleJobs() {

    try {
      SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

      scheduler = schedFact.getScheduler();
      log.debug("scheduler ---> " + scheduler);
      pain010Job = JobBuilder
          .newJob(AC_Pain010ExtractJob.class)
          .withIdentity(job4Key)
          .build();

      pain010Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(pain010TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("10 0/10 * * * ?"))
          .build();

      pain012Job = JobBuilder
          .newJob(AC_Pain012ExtractJob.class)
          .withIdentity(job6Key)
          .build();


      pain012Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(pain012TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("10 0/10 * * * ?"))
          .build();

      startOfDayJob = JobBuilder
          .newJob(StartofDayJob.class)
          .withIdentity(job8Key)
          .build();

      startOfDayTrigger = TriggerBuilder
          .newTrigger()
          .withIdentity(startofdayTK)
          .startNow()
          .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(07, 00))
          // .withSchedule(CronScheduleBuilder.cronSchedule(mdtSysctrlCronEntity.getCronTime()))
          .build();

      pacs002ExtJob = JobBuilder
          .newJob(AC_Pacs002_ExtractJob.class)
          .withIdentity(job10Key)
          .build();

      pacs002Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(pacs002TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("10 0/10 * * * ?"))
          .build();

      st100Job = JobBuilder
          .newJob(AC_ST200ExtractJob.class)
          .withIdentity(jobKey11)
          .build();

      st100Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(st100TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
          .build();


      st102Job = JobBuilder
          .newJob(AC_ST202ExtractJob.class)
          .withIdentity(jobKey12)
          .build();

      st102Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(st102TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
          .build();

      st104Job = JobBuilder
          .newJob(AC_ST204ExtractJob.class)
          .withIdentity(jobKey13)
          .build();

      st104Trigger = TriggerBuilder
          .newTrigger()
          .withIdentity(st104TK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
          .build();

      billTrigger = TriggerBuilder
          .newTrigger()
          .withIdentity(billTK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 1/1 * ? *")) //Runs every hour
          .build();

      billJob = JobBuilder
          .newJob(BillingExportJob.class)
          .withIdentity(jobKey22)
          .build();

      eotTrigger = TriggerBuilder
          .newTrigger()
          .withIdentity(eotTK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
          .build();

      eotJob = JobBuilder
          .newJob(EotExtractJob.class)
          .withIdentity(jobKey23)
          .build();

      reportJob = JobBuilder
          .newJob(DailyReportsJob.class)
          .withIdentity(jobKey26)
          .build();

      reportTrigger = TriggerBuilder
          .newTrigger()
          .withIdentity(reportTK)
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule(cronValue))
          .build();

      scheduler.start();
      try {
        //wait for 30 seconds to finish the job
        Thread.sleep(30000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      printJobsAndTriggers(scheduler);

    } catch (SchedulerException e) {
      log.error("Error while creating scheduler", e);
    }
  }


  private void printJobsAndTriggers(Scheduler scheduler) throws SchedulerException {
    log.debug("Quartz Scheduler :{}", scheduler.getSchedulerName());

    for (String group : scheduler.getJobGroupNames()) {
      for (JobKey jobkey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))) {

        log.debug("Found job identified by {}", jobkey);
      }
    }
    for (String group : scheduler.getTriggerGroupNames()) {

      for (TriggerKey triggerKey : scheduler.getTriggerKeys(
          GroupMatcher.<TriggerKey>groupEquals(group))) {

        log.debug("Found trigger identified by {}", triggerKey);

      }
    }
  }


  @PreDestroy
  public void shutDownScheduler() {
    if (scheduler != null) {
      try {
        scheduler.shutdown(false);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to shut down scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  //When you want to start the entire scheduler after shutting it down
  public boolean startAllSchedulers() {
    boolean startAllSch = false;
    if (scheduler != null) {
      try {
        scheduler.start();

        startPain010();
        startPain012();
        startST100();
        startST102();
        startPacs002();
        startST104();
        startBilling();
        startEotExtract();
        startDlyReports();
        boolean saved = updateOpsSchedulerDetails("All", null, "Y");
        startAllSch = true;
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start up all schedulers: ", e.getMessage());
        e.printStackTrace();
        startAllSch = false;
      }
    }
    return startAllSch;
  }

  //When you want to start the entire scheduler after shutting it down
  public boolean stopAllSchedulers() {
    boolean stopAllSch = false;
    if (scheduler != null) {
      try {
        unschedulePain010();
        unschedulePain012();
        unscheduleST100();
        unscheduleST102();
        unschedulePacs002();
        unscheduleST104();
        unscheduleBilling();
        unscheduleEotExtract();
        unscheduleReportGen();

        boolean saved = updateOpsSchedulerDetails("All", null, "N");
        stopAllSch = true;
      } catch (Exception e) {
        log.error("An error occurred whilst trying to stop all schedulers: ", e.getMessage());
        e.printStackTrace();
        stopAllSch = false;
      }
    }
    return stopAllSch;
  }

  public boolean rescheduleAllSchedulers(String rescheduleCron, String cronTime) {
    boolean reschAllSch = false;
    if (scheduler != null) {
      try {
        reschedulePain010(rescheduleCron, cronTime);
        reschedulePain012(rescheduleCron, cronTime);
        rescheduleST100(rescheduleCron, cronTime);
        rescheduleST102(rescheduleCron, cronTime);
        reschedulePacs002(rescheduleCron, cronTime);
        rescheduleST104(rescheduleCron, cronTime);
        rescheduleBilling(rescheduleCron, cronTime);
        rescheduleEotExtract(rescheduleCron, cronTime);
        rescheduleDlyReports(rescheduleCron, cronTime);

        boolean saved = updateOpsSchedulerDetails("All", cronTime, "Y");
        reschAllSch = true;
      } catch (Exception e) {
        log.error("An error occurred whilst trying to reschedule all schedulers: ", e.getMessage());
        e.printStackTrace();
        reschAllSch = false;
      }
    }
    return reschAllSch;
  }

  //===============================STARTS METHODS==================================//
  //start delayed jobs ........ in a case where manual intervention is required

  public boolean startPain010() {
    boolean manomChk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(job4Key);
        log.info("Trying to start pain010 scheduler--->");
        scheduler.scheduleJob(pain010Job, pain010Trigger);
        boolean saved = updateOpsSchedulerDetails("MANOM", null, "Y");
        if (saved) {
          manomChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        manomChk = false;
      }
    }
    return manomChk;
  }


  public boolean startPain012() {
    boolean manocChk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(job6Key);
        log.info("Trying to start pain012 scheduler--->");
        scheduler.scheduleJob(pain012Job, pain012Trigger);
        boolean saved = updateOpsSchedulerDetails("MANOC", null, "Y");
        if (saved) {
          manocChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        manocChk = false;
      }
    }
    return manocChk;
  }


  public boolean startPacs002() {
    boolean pacs002Chk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(job9Key);
        log.info("Trying to start pacs002 scheduler--->");
        scheduler.scheduleJob(pacs002ExtJob, pacs002Trigger);
        boolean saved = updateOpsSchedulerDetails("ST103", null, "Y");
        if (saved) {
          pacs002Chk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        pacs002Chk = false;
      }
    }
    return pacs002Chk;
  }

  public boolean startST100() {
    boolean st100SchChk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(jobKey11);
        log.info("Trying to start ST100 scheduler--->");
        scheduler.scheduleJob(st100Job, st100Trigger);
        boolean saved = updateOpsSchedulerDetails("ST100", null, "Y");
        if (saved) {
          st100SchChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        st100SchChk = false;
      }
    }
    return st100SchChk;
  }

  public boolean startST102() {
    boolean st102Chk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(jobKey12);
        log.info("Trying to start ST102 scheduler--->");
        scheduler.scheduleJob(st102Job, st102Trigger);
        boolean saved = updateOpsSchedulerDetails("ST102", null, "Y");
        if (saved) {
          st102Chk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        st102Chk = false;
      }
    }
    return st102Chk;
  }

  public boolean startST104() {
    boolean st104Chk = false;
    if (scheduler != null) {
      try {
        //				scheduler.triggerJob(jobKey13);
        log.info("Trying to start ST104 scheduler--->");
        scheduler.scheduleJob(st104Job, st104Trigger);
        boolean saved = updateOpsSchedulerDetails("ST104", null, "Y");
        if (saved) {
          st104Chk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        st104Chk = false;
      }
    }
    return st104Chk;
  }

  public boolean startBilling() {
    boolean billChk = false;
    if (scheduler != null) {
      try {
        log.info("Trying to start Billing Export scheduler--->");
        scheduler.scheduleJob(billJob, billTrigger);
        boolean saved = updateOpsSchedulerDetails("Billing Export", null, "Y");
        if (saved) {
          billChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        billChk = false;
      }
    }
    return billChk;
  }


  public boolean startEotExtract() {
    boolean eotChk = false;
    if (scheduler != null) {
      try {
        log.info("Trying to start Eot Extract scheduler--->");
        scheduler.scheduleJob(eotJob, eotTrigger);
        boolean saved = updateOpsSchedulerDetails("Eot Extract", null, "Y");
        if (saved) {
          eotChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        eotChk = false;
      }
    }
    return eotChk;
  }


  public boolean startDlyReports() {
    getCronValue();
    boolean reportChk = false;

    Calendar calend = Calendar.getInstance();

    if (scheduler != null) {
      try {
        // scheduler.triggerJob(jobKey24);
        log.info("Trying to start Daily Reports scheduler--->");
        scheduler.scheduleJob(reportJob, reportTrigger);
        boolean saved = updateOpsSchedulerDetails("Reports", null, "Y");
        if (saved) {
          reportChk = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
        reportChk = false;
      }
    }
    return reportChk;
  }

  //===============================PAUSE METHODS==================================//
  public void pausePain010() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(pain010TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pausePain012() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(pain012TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pausePacs002() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(pacs002TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pauseST100() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(st100TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pauseST102() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(st102TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pauseST104() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(st104TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void pauseEotFileExtract() {
    if (scheduler != null) {
      try {
        scheduler.pauseTrigger(eotTK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to pause/stop scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  //===============================RESUME METHODS==================================//
  //Resuming specific triggers in case they have been unscheduled

  public void resumePain010() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(pain010TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void resumePain012() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(pain012TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }


  public void resumePacs002() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(pacs002TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void resumeST100() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(st100TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void resumeST102() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(st102TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void resumeST104() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(st104TK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void resumeEotFileExtract() {
    if (scheduler != null) {
      try {
        scheduler.resumeTrigger(eotTK);
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to start/resume scheduler", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  //===============================RESCHEDULE METHODS==================================//

  public boolean reschedulePain010(String rescheduleCron, String cronTime) {
    boolean reschPain010 = false;
    try {
      TriggerBuilder oldTrigger = pain010Trigger.getTriggerBuilder();

      pain010Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(pain010TK, pain010Trigger);
      boolean saved = updateOpsSchedulerDetails("MANOM", cronTime, "Y");
      if (saved) {
        reschPain010 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschPain010 = false;
    }
    return reschPain010;
  }

  public boolean reschedulePain012(String rescheduleCron, String cronTime) {
    boolean reschPain012 = false;
    try {
      TriggerBuilder oldTrigger = pain012Trigger.getTriggerBuilder();

      pain012Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(pain012TK, pain012Trigger);

      boolean saved = updateOpsSchedulerDetails("MANOC", cronTime, "Y");
      if (saved) {
        reschPain012 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschPain012 = false;
    }
    return reschPain012;
  }

  public boolean reschedulePacs002(String rescheduleCron, String cronTime) {
    boolean reschPacs002 = false;
    try {
      TriggerBuilder oldTrigger = pacs002Trigger.getTriggerBuilder();

      pacs002Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(pacs002TK, pacs002Trigger);

      boolean saved = updateOpsSchedulerDetails("ST103", cronTime, "Y");
      if (saved) {
        reschPacs002 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschPacs002 = false;
    }
    return reschPacs002;
  }

  public boolean rescheduleST100(String rescheduleCron, String cronTime) {
    boolean reschST100 = false;
    try {
      TriggerBuilder oldTrigger = st100Trigger.getTriggerBuilder();

      st100Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(st100TK, st100Trigger);

      boolean saved = updateOpsSchedulerDetails("ST100", cronTime, "Y");
      if (saved) {
        reschST100 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschST100 = false;
    }
    return reschST100;
  }

  public boolean rescheduleST102(String rescheduleCron, String cronTime) {
    boolean reschST102 = false;
    try {
      TriggerBuilder oldTrigger = st102Trigger.getTriggerBuilder();

      st102Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(st102TK, st102Trigger);

      boolean saved = updateOpsSchedulerDetails("ST102", cronTime, "Y");
      if (saved) {
        reschST102 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschST102 = false;
    }
    return reschST102;
  }

  public boolean rescheduleST104(String rescheduleCron, String cronTime) {
    boolean reschST104 = false;
    try {
      TriggerBuilder oldTrigger = st104Trigger.getTriggerBuilder();

      st104Trigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(st104TK, st104Trigger);

      boolean saved = updateOpsSchedulerDetails("ST104", cronTime, "Y");
      if (saved) {
        reschST104 = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschST104 = false;
    }
    return reschST104;
  }

  public boolean rescheduleBilling(String rescheduleCron, String cronTime) {
    boolean reschBill = false;
    try {
      TriggerBuilder oldTrigger = billTrigger.getTriggerBuilder();

      billTrigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(billTK, billTrigger);

      boolean saved = updateOpsSchedulerDetails("Billing Export", cronTime, "Y");
      if (saved) {
        reschBill = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschBill = false;
    }
    return reschBill;
  }

  public boolean rescheduleEotExtract(String rescheduleCron, String cronTime) {
    boolean reschBill = false;
    try {
      TriggerBuilder oldTrigger = eotTrigger.getTriggerBuilder();

      billTrigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(eotTK, eotTrigger);

      boolean saved = updateOpsSchedulerDetails("Eot Extract", cronTime, "Y");
      if (saved) {
        reschBill = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule scheduler", e.getMessage());
      e.printStackTrace();
      reschBill = false;
    }
    return reschBill;
  }

  public boolean rescheduleDlyReports(String rescheduleCron, String cronTime) {
    boolean reschReports = false;
    try {
      TriggerBuilder oldTrigger = reportTrigger.getTriggerBuilder();

      reportTrigger =
          oldTrigger.withSchedule(CronScheduleBuilder.cronSchedule(rescheduleCron)).build();
      scheduler.rescheduleJob(reportTK, reportTrigger);

      boolean saved = updateOpsSchedulerDetails("Reports", cronTime, "Y");
      if (saved) {
        reschReports = true;
      }
    } catch (SchedulerException e) {
      log.error("An error occurred whilst trying to reschedule Dly Reports scheduler",
          e.getMessage());
      e.printStackTrace();
      reschReports = false;
    }
    return reschReports;
  }

  //===============================UNSCHEDULE METHODS==================================//

  public boolean unschedulePain010() {
    boolean pain010Stop = false;
    if (scheduler != null) {
      try {
        pain010Stop = scheduler.unscheduleJob(pain010TK);
        boolean saved = updateOpsSchedulerDetails("MANOM", null, "N");
        if (saved) {
          pain010Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        pain010Stop = false;
      }
    }
    return pain010Stop;
  }

  public boolean unschedulePain012() {
    boolean pain012Stop = false;
    if (scheduler != null) {
      try {
        pain012Stop = scheduler.unscheduleJob(pain012TK);
        boolean saved = updateOpsSchedulerDetails("MANOC", null, "N");
        if (saved) {
          pain012Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        pain012Stop = false;
      }
    }
    return pain012Stop;
  }

  public boolean unschedulePacs002() {
    boolean pacs002Stop = false;
    if (scheduler != null) {
      try {
        pacs002Stop = scheduler.unscheduleJob(pacs002TK);
        boolean saved = updateOpsSchedulerDetails("ST103", null, "N");
        if (saved) {
          pacs002Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        pacs002Stop = false;
      }
    }
    return pacs002Stop;
  }

  public boolean unscheduleST100() {
    boolean st100Stop = false;
    if (scheduler != null) {
      try {
        st100Stop = scheduler.unscheduleJob(st100TK);
        boolean saved = updateOpsSchedulerDetails("ST100", null, "N");
        if (saved) {
          st100Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        st100Stop = false;
      }
    }
    return st100Stop;
  }

  public boolean unscheduleST102() {
    boolean st102Stop = false;
    if (scheduler != null) {
      try {
        st102Stop = scheduler.unscheduleJob(st102TK);
        boolean saved = updateOpsSchedulerDetails("ST102", null, "N");
        if (saved) {
          st102Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        st102Stop = false;
      }
    }
    return st102Stop;
  }

  public boolean unscheduleST104() {
    boolean st104Stop = false;
    if (scheduler != null) {
      try {
        st104Stop = scheduler.unscheduleJob(st104TK);
        boolean saved = updateOpsSchedulerDetails("ST104", null, "N");
        if (saved) {
          st104Stop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        st104Stop = true;
      }
    }
    return st104Stop;
  }

  public boolean unscheduleBilling() {
    boolean billStop = false;
    if (scheduler != null) {
      try {
        billStop = scheduler.unscheduleJob(billTK);
        boolean saved = updateOpsSchedulerDetails("Billing Export", null, "N");
        if (saved) {
          billStop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        billStop = false;
      }
    }
    return billStop;
  }

  @Override
  public boolean unscheduleEotExtract() {
    boolean eotExtractStop = false;
    if (scheduler != null) {
      try {
        eotExtractStop = scheduler.unscheduleJob(eotTK);
        boolean saved = updateOpsSchedulerDetails("Eot Extract", null, "N");
        if (saved) {
          eotExtractStop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        eotExtractStop = false;
      }
    }
    return eotExtractStop;
  }

  public boolean unscheduleReportGen() {
    boolean reportStop = false;
    if (scheduler != null) {
      try {
        reportStop = scheduler.unscheduleJob(reportTK);
        boolean saved = updateOpsSchedulerDetails("Reports", null, "N");
        if (saved) {
          reportStop = true;
        }
      } catch (SchedulerException e) {
        log.error("An error occurred whilst trying to stop scheduler", e.getMessage());
        e.printStackTrace();
        reportStop = false;
      }
    }
    return reportStop;
  }

  public boolean updateOpsSchedulerDetails(String schedulerName, String reschTime,
                                           String activeInd) {
    boolean saved = false;
    //	Retrieve the opsScheduler
    CasOpsSchedulerEntity casOpsSchedulerEntity = new CasOpsSchedulerEntity();

    try {
      casOpsSchedulerEntity = (CasOpsSchedulerEntity) genericDAO.findByNamedQuery(
          "CasOpsSchedulerEntity.findBySchedulerName", "schedulerName", schedulerName);
      log.debug("casOpsSchedulerEntity from DB: " + casOpsSchedulerEntity);
      if (casOpsSchedulerEntity != null) {
        if (activeInd != null) {
          casOpsSchedulerEntity.setActiveInd(activeInd);
        }

        if (reschTime != null) {
          casOpsSchedulerEntity.setRescheduleTime(reschTime);
        }

        casOpsSchedulerEntity.setModifiedBy(systemName);
        casOpsSchedulerEntity.setModifiedDate(new Date());
        log.debug("mdtAcOpsSchedulerEntity before save: " + casOpsSchedulerEntity);

        saved = adminBeanRemote.createOpsScheduler(casOpsSchedulerEntity);
        log.debug("OPS SCH SAVED---> " + saved);
      }
    } catch (ObjectNotFoundException onfe) {
      log.info("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieving Ops Schedulers: " + e.getMessage());
      e.printStackTrace();
    }
    return saved;
  }

  @Override
  public Object getCronValue() {
    SchedulerCronModel schedulerCronModel = null;

    try {
      String cronRptInterval = "dlyrpt cron value";

      CasSysctrlSchedulerCronEntity casSysctrlSchedulerCronEntity =
          new CasSysctrlSchedulerCronEntity();
      casSysctrlSchedulerCronEntity = (CasSysctrlSchedulerCronEntity) genericDAO.findByNamedQuery(
          "CasSysctrlSchedulerCronEntity.findBySchedulerCronInterval", "schedulerCronInterval",
          cronRptInterval);

      if (casSysctrlSchedulerCronEntity != null) {
        schedulerCronModel = new SchedulerCronModel();
        schedulerCronModel = new AdminTranslator().translateSchedulerCronEntityToModel(
            casSysctrlSchedulerCronEntity);

        log.debug(schedulerCronModel.toString());

        cronValue = schedulerCronModel.getCronValue();

        log.info("Cron value is  :" + cronValue);

        reportTrigger = TriggerBuilder
            .newTrigger()
            .withIdentity(reportTK)
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule(cronValue))
            .build();
      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on getCronValue :" + e.getMessage());
      e.printStackTrace();
    }

    return schedulerCronModel;
  }

  public String[] splitCronString(String cronValue) {
    String[] split = cronValue.split(" ");

    return split;

  }

  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }
}






