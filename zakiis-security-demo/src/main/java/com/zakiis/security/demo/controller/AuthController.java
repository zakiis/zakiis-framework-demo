package com.zakiis.security.demo.controller;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.core.util.JsonUtil;
import com.zakiis.security.annotation.Permission;
import com.zakiis.security.annotation.RateLimit;
import com.zakiis.security.demo.domain.constants.SecurityConstant;
import com.zakiis.security.demo.domain.dto.Response;
import com.zakiis.security.demo.domain.dto.user.LoginRequest;
import com.zakiis.security.demo.domain.dto.user.UserInfo;
import com.zakiis.security.demo.service.FakeUserService;
import com.zakiis.security.jwt.JWTUtil;
import com.zakiis.security.jwt.algorithm.Algorithm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final FakeUserService fakeUserService;

	@PostMapping("login")
	@Permission(bypass = true)
	public Response<String> login(@RequestBody @Valid LoginRequest reqeust) {
		UserInfo userInfo = fakeUserService.selectByUsername(reqeust.getUsername());
		if (!userInfo.getUsername().equals(reqeust.getUsername()) || !userInfo.getPassword().equals(reqeust.getPassword())) {
			throw new ZakiisRuntimeException("Username or password not correct.");
		}
		Date issueAt = new Date();
		Date expireAt = DateUtils.addMinutes(issueAt, 30);
		String token = JWTUtil.create()
			.withSubject(String.valueOf(userInfo.getUserId()))
			.withIssuedAt(issueAt)
			.withExpiresAt(expireAt)
			.withClaim("userInfo", JsonUtil.toJson(userInfo))
			.sign(Algorithm.HMAC256(SecurityConstant.HMAC_SHA_256_KEY));
		return Response.success(token);
	}
	
	@PostMapping("code")
	@Permission(bypass = true)
	@RateLimit(limitKeyEL = "#phone", minIntervalEL = "10", maxRequestPerDayEL = "5")
	public Response<String> sendCode(@RequestParam String phone) {
		return Response.success(RandomStringUtils.randomNumeric(4));
	}
}
