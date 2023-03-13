package com.zakiis.user.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.core.util.AssertUtil;
import com.zakiis.security.annotation.OptimisticLock;
import com.zakiis.user.demo.domain.dto.Response;
import com.zakiis.user.demo.domain.dto.user.UserInfo;
import com.zakiis.user.demo.service.FakeUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserInfoController {
	
	private final FakeUserService fakeUserService;

	@GetMapping("/user/detail/{userId}")
	public Response<UserInfo> userInfo(@PathVariable Long userId) {
		UserInfo dbUser = fakeUserService.selectByUserId(userId);
		AssertUtil.notNull(dbUser, "User not exists");
		return Response.success(dbUser);
	}

	@PutMapping("/user/{userId}")
	@OptimisticLock(lockKeyEL = "#userId", lockTimeout = 10)
	public Response<Object> modifyUser(@PathVariable Long userId, UserInfo userInfo) throws InterruptedException {
		userInfo.setUserId(userId);
		log.info("Received user modify request, userInfo:{}", userInfo);
		UserInfo dbUser = fakeUserService.selectByUserId(userInfo.getUserId());
		AssertUtil.notNull(dbUser, "User not exists");
		return Response.success();
	}
	
	@DeleteMapping("/user/{userId}")
	public Response<Object> deleteUser(@PathVariable Long userId) {
		log.info("Received user delete request, user Id:{}", userId);
		return Response.success();
	}
}
