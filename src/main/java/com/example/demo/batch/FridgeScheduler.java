package com.example.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FridgeScheduler {
	
	@Autowired
    JobLauncher batch1JobLauncher;
	
	@Autowired
	Job updateUserJob;
	
	 @Scheduled(cron = "0 0 12 * * ?")
	  public void launchJob1() throws Exception {
	      JobParameters jobParameters = new JobParametersBuilder()
	              .addLong("time", System.currentTimeMillis())
	              .toJobParameters();

	      batch1JobLauncher.run(updateUserJob, jobParameters);
	  }

}
