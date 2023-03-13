package com.zakiis.gatway.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class GatewayDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;

	@Test
	void contextLoads() {
	}

	
}
