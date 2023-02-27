package com.zakiis.web.demo.infra.dependency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ZAKIIS-LOG-DEMO", url = "localhost:${server.port}/demo")
public interface DemoFeignClient {

	@GetMapping("hello")
	public String hello();
	
	@PostMapping("notExistsPath")
	public String notExistsPath();
	
}
