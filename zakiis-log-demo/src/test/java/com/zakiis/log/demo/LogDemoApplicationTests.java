package com.zakiis.log.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestTemplate;

import com.zakiis.log.demo.infra.dependency.DemoFeignClient;
import com.zakiis.log.holder.TraceIdHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class LogDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DemoFeignClient demoFeignClient;

	@Test
	void contextLoads() {
		testTraceId();
		testDesensitization();
	}

	private void testTraceId() {
		String url = String.format("http://localhost:%s/demo/hello", port);
		String traceId = "LOG_DEMO_pass_trace_id_in_header";
		TraceIdHolder.set(traceId);
		log.info("Method trace id would be: {}'", TraceIdHolder.get());
		restTemplate.getForEntity(url, String.class);
		demoFeignClient.hello();
	}
	
	private void testDesensitization() {
		log.info("{\"userName\":\"zhangsan123\", \"password\":\"123456\", \"mobile\":\"13112341234\", \"sex\":\"male\", \"country\":\"China\", \"age\":65}");
		log.info("{userName=zhangsan123, age = , password=123456, mobile = 13112341234, sex=male, country=China}");
		log.info("add user start, param: {\"userName\":\"zhangsan123\", \"password\":\"123456\", \"mobile\":\"13112341234\", \"sex\":\"male\", \"country\":\"China\", \"age\":65}");
		log.info("add user start, params: {userName=zhangsan123, age = , password=123456, mobile = 13112341234, sex=male, country=China}");
	}
}
