package com.zakiis.user.demo.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.security.annotation.RateLimit;
import com.zakiis.user.demo.domain.dto.Response;
import com.zakiis.user.demo.domain.dto.user.LoginRequest;
import com.zakiis.user.demo.domain.dto.user.UserInfo;
import com.zakiis.user.demo.service.FakeUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final FakeUserService fakeUserService;

	@PostMapping("login")
	public Response<UserInfo> login(@RequestBody @Valid LoginRequest reqeust) {
		UserInfo userInfo = fakeUserService.selectByUsername(reqeust.getUsername());
		if (!userInfo.getUsername().equals(reqeust.getUsername()) || !userInfo.getPassword().equals(reqeust.getPassword())) {
			throw new ZakiisRuntimeException("Username or password not correct.");
		}
		return Response.success(userInfo);
	}
	
	@PostMapping("code")
	@RateLimit(limitKeyEL = "#phone", minIntervalEL = "10", maxRequestPerDayEL = "5")
	public Response<String> sendCode(@RequestParam String phone) {
		return Response.success(RandomStringUtils.randomNumeric(4));
	}
}
