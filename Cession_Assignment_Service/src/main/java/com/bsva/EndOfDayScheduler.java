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

import com.bsva.Jobs.EndofDayJob;

@Startup
@Singleton
public class EndOfDayScheduler {

	 private Logger log = LoggerFactory.getLogger(EndOfDayScheduler.class);
	 private  Scheduler scheduler;

    @Inject
     private JobFactory cdiJobFactory;
    
    public void scheduleEndofDayJob()
    {
   		
		try 
		{
			scheduler = new StdSchedulerFactory().getScheduler();
 			
			
			JobKey endofDaykey = JobKey.jobKey("EndofDayJob","my-jobs");
			JobDetail job7 =JobBuilder
					        .newJob(EndofDayJob.class)
					        .withIdentity(endofDaykey)
					        .build();
			
			 
		       TriggerKey tk7 = TriggerKey.triggerKey("trigger7", "my-jobs");
				Trigger trigger7 = TriggerBuilder
				                   .newTrigger()
				                   .withIdentity(tk7)
				
				                    .startNow()
				
				                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
				
				                    .build();	
				
				scheduler.scheduleJob(job7,trigger7);
				  scheduler.start();
				}
		 catch (SchedulerException e)
		 
					{
						log.info("Error while creating scheduler",e);
					}
						}

    @PreDestroy
    public void stopJobs() {

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