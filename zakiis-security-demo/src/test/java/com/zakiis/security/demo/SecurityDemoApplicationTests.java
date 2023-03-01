package com.zakiis.security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.security.demo.domain.constants.SecurityConstant;
import com.zakiis.security.demo.domain.dto.Response;
import com.zakiis.security.demo.domain.dto.user.LoginRequest;
import com.zakiis.security.demo.domain.dto.user.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() throws InterruptedException {
		String jwtToken = login();
		userInfoQuery(jwtToken);
		userInfoDelete(jwtToken);
		testOptimisticLock(jwtToken);
	}

	private String login() {
		String url = String.format("http://localhost:%s/auth/login", port);
		LoginRequest loginRequest = LoginRequest.of("zhangsan", "123456");
		ResponseEntity<Response<String>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(loginRequest),
				new ParameterizedTypeReference<Response<String>>() {});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			log.info("login success, jwt token:{}", responseEntity.getBody().getData());
			return responseEntity.getBody().getData();
		}
		throw new ZakiisRuntimeException("login failed: " + responseEntity.getStatusCode());
	}
	
	private void userInfoQuery(String jwtToken) {
		String url = String.format("http://localhost:%s/user/info", port);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(SecurityConstant.TOKEN_HEADER_NAME, jwtToken);
		HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<Response<UserInfo>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<Response<UserInfo>>() {});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			log.info("query user info success: {}", responseEntity.getBody().getData());
		} else {
			throw new ZakiisRuntimeException("query user info failed: " + responseEntity.getStatusCode());
		}
	}
	
	private void userInfoDelete(String jwtToken) {
		String url = String.format("http://localhost:%s/user", port);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(SecurityConstant.TOKEN_HEADER_NAME, jwtToken);
		HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
		try {
			ResponseEntity<Response<UserInfo>> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity,
					new ParameterizedTypeReference<Response<UserInfo>>() {});
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				if (responseEntity.getBody().isSuccess()) {
					log.info("delete user success");
				} else {
					log.info("delete user failed: {}", responseEntity.getBody());
				}
			}
		} catch (HttpClientErrorException e) {
			String bodyAsString = e.getResponseBodyAsString();
			log.info("delete user failed: {}", bodyAsString);
		}
	}
	
	private void testOptimisticLock(String jwtToken) throws InterruptedException {
		String url = String.format("http://localhost:%s/user/1", port);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(SecurityConstant.TOKEN_HEADER_NAME, jwtToken);
		HttpEntity<Object> httpEntity = new HttpEntity<>(null, httpHeaders);
		Thread t1 = createUserModifyThread(url, httpEntity);
		Thread t2 = createUserModifyThread(url, httpEntity);
		Thread t3 = createUserModifyThread(url, httpEntity);
		t1.start();
		t2.start();
		Thread.sleep(11 * 1000L);
		t3.start();
		Thread.sleep(20 * 1000L);
	}
	
	private Thread createUserModifyThread(String url, HttpEntity<Object> httpEntity) {
		return new Thread(() -> {
			ResponseEntity<Response<Object>> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
					new ParameterizedTypeReference<Response<Object>>() {});
			log.info("modify user returns:{}", responseEntity.getBody());
		});
		
	}

}
