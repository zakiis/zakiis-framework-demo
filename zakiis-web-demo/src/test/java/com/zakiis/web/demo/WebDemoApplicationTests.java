package com.zakiis.web.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zakiis.web.demo.infra.dependency.DemoFeignClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class WebDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DemoFeignClient demoFeignClient;

	@Test
	void contextLoads() {
		testRestTemplate();
		testFeignClient();
	}

	private void testRestTemplate() {
		String url = String.format("http://localhost:%s/demo/hello", port);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		log.info("GET /demo/hello API using rest template returns {}", responseEntity.getBody());
		url = String.format("http://localhost:%s/demo/notExistsPath", port);
		responseEntity = restTemplate.getForEntity(url, String.class);
		log.info("GET /demo/notExistsPaht API using rest template returns {}", responseEntity.getBody());
	}

	private void testFeignClient() {
		String ret = demoFeignClient.hello();
		log.info("GET /demo/hello API using feign client returns {}", ret);
		ret = demoFeignClient.notExistsPath();
		log.info("POST /demo/notExistsPath API using feign client returns {}", ret);
	}
}
