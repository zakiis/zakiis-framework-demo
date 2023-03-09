package com.zakiis.rdb.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zakiis.rdb.demo.mapper")
@SpringBootApplication
public class RdbDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdbDemoApplication.class, args);
	}

}
