package com.zakiis.job.demo.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldJob extends QuartzJobBean {
	
	public static final String JOB_GROUP = "zakiis";
	public static final String JOB_NAME = "hello world";
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Trigger trigger = context.getTrigger();
		Date fireTime = context.getFireTime();
		log.info("hello world job executed, trigger:{} , fire time:{}, data map:{}", trigger, fireTime, jobDataMap);
	}

}
