package com.zakiis.job.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure.class)
@SpringBootApplication
public class JobDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobDemoApplication.class, args);
	}
	
}
