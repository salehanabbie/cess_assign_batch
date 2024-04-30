/*package com.bsva;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsva.Jobs.StartofDayJob;


*//**
 * @author ElelwaniR
 *//*

@Startup
@Singleton
public class StartOfDayScheduler {

	 private Logger log = LoggerFactory.getLogger(StartOfDayScheduler.class);
     private  Scheduler scheduler;

     @Inject
      private JobFactory cdiJobFactory;

     
     public void scheduleStartofDayJob()
     {
    		
 		try 
 		{
 			scheduler = new StdSchedulerFactory().getScheduler();
 			
 			
			JobKey startofDayJobKey = JobKey.jobKey("StartofDayJob","my-jobs");
			JobDetail job8 =JobBuilder
					        .newJob(StartofDayJob.class)
					        .withIdentity(startofDayJobKey)
					        .build();
			 
		       TriggerKey startofDayTrigger = TriggerKey.triggerKey("startofDayTrigger", "my-jobs");
				Trigger trigger8 = TriggerBuilder
				                   .newTrigger()
				                   .withIdentity(startofDayTrigger)
				                   .startNow()
				                   .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
				                   .build();	
				
				scheduler.scheduleJob(job8,trigger8);
     			 scheduler.start();
				 printJobsAndTriggers(scheduler);
				 
 		 		}
		catch (SchedulerException e)
		{
			log.info("Error while creating scheduler",e);
			
				}
     		}
     
     
     private void printJobsAndTriggers(Scheduler scheduler ) throws SchedulerException
 		{
    	 log.info("Quartz Scheduler :{}",scheduler.getSchedulerName());
    	 log.info("Found job identified by {}",startofDayJobKey);
 			}
@PreDestroy
public void stopStartofDay() {

    if (scheduler != null) {

        try {

          scheduler.shutdown(false);

        } 
        catch (SchedulerException e) {

            log.error("Error while closing scheduler", e);

        }

    }
}
}*/