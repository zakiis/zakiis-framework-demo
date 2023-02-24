package com.zakiis.job.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zakiis.core.util.JsonUtil;
import com.zakiis.job.demo.job.HelloWorldJob;
import com.zakiis.job.dto.JobInfo;
import com.zakiis.job.service.QuartzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class JobDemoApplicationTests {
	
	@Autowired
	private QuartzService quartzService;

	@Test
	void contextLoads() throws InterruptedException {
		List<JobInfo> jobList = queryAllJob();
		deleteJob(jobList);
		createJob();
		Thread.sleep(10000L);
		pauseJob();
		Thread.sleep(10000L);
		resumeJob();
		Thread.sleep(10000L);
	}

	private List<JobInfo> queryAllJob() {
		List<JobInfo> jobList = quartzService.queryAllJob();
		log.info("job list: {}", jobList);
		return jobList;
	}

	private void deleteJob(List<JobInfo> jobList) {
		/**
		 * DELETE from QRTZ_CRON_TRIGGERS where SCHED_NAME = 'quartzScheduler' and TRIGGER_NAME = '${jobName}' and TRIGGER_GROUP  = '${jobGroup}';
		 * delete from QRTZ_TRIGGERS where SCHED_NAME = 'quartzScheduler' and TRIGGER_NAME = '${jobName}' and TRIGGER_GROUP  = '${jobGroup}';
		 * DELETE FROM QRTZ_JOB_DETAILS where SCHED_NAME = 'quartzScheduler' and JOB_NAME = '${jobName}' and JOB_GROUP = '${jobGroup}';
		 */
		for (JobInfo jobDTO : jobList) {
			quartzService.deleteJob(jobDTO.getJobName(), jobDTO.getJobGroupName());
		}
	}
	
	private void createJob() {
		// Job data is optional
		Map<String, Object> jobData = new HashMap<String, Object>();
		jobData.put("name", "Jack");
		quartzService.addCronJob(HelloWorldJob.class, HelloWorldJob.JOB_NAME, HelloWorldJob.JOB_GROUP, "0/2 * * * * ?", jobData);
		log.info("Job {} created.", HelloWorldJob.JOB_NAME);
	}
	
	private void pauseJob() {
		quartzService.pauseJob(HelloWorldJob.JOB_NAME, HelloWorldJob.JOB_GROUP);
		JobInfo helloJob = quartzService.queryJob(HelloWorldJob.JOB_NAME, HelloWorldJob.JOB_GROUP).get(0);
		log.info("Job paused: {}" + JsonUtil.toJson(helloJob));
	}
	
	private void resumeJob() {
		quartzService.resumeJob(HelloWorldJob.JOB_NAME, HelloWorldJob.JOB_GROUP);
		JobInfo jobDTO = quartzService.queryJob(HelloWorldJob.JOB_NAME, HelloWorldJob.JOB_GROUP).get(0);
		log.info("Job resumed: {}" + JsonUtil.toJson(jobDTO));
	}
}
