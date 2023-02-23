package com.zakiis.log.demo.infra.dependency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ZAKIIS-LOG-DEMO", url = "localhost:${server.port}/demo")
public interface DemoFeignClient {

	@GetMapping("hello")
	public String hello();
	
}
