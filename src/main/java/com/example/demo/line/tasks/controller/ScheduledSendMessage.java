package com.example.demo.line.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.line.tasks.service.ProcessExpirationService;

@RestController
@RequestMapping
public class ScheduledSendMessage {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProcessExpirationService processExpirationService;
	
	//定時任務
	@Scheduled(cron = "0 0 12 * * ?")
	public void pushExpirationMessage() {
		try {
			logger.info("SCHEDULED START");
			processExpirationService.pushExpirationMessage();
			logger.info("SCHEDULED END");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
