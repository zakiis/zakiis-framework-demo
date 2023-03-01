package com.zakiis.security.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zakiis.security.annotation.OptimisticLock;
import com.zakiis.security.annotation.Permission;
import com.zakiis.security.demo.domain.constants.FunctionCode;
import com.zakiis.security.demo.domain.dto.Response;
import com.zakiis.security.demo.domain.dto.user.UserInfo;
import com.zakiis.security.demo.service.holder.LoginUserHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserInfoController {

	@GetMapping("/user/info")
	@Permission(code = FunctionCode.USER_INFO_QUERY)
	public Response<UserInfo> userInfo() {
		return Response.success(LoginUserHolder.get());
	}

	@PutMapping("/user/{userId}")
	@Permission(code = FunctionCode.USER_INFO_MODIFY)
	@OptimisticLock(lockKeyEL = "#userId", lockTimeout = 10)
	public Response<Object> modifyUser(@PathVariable Long userId) throws InterruptedException {
		log.info("Received user modify request, userInfo:{}", LoginUserHolder.get());
		Thread.sleep(15000L);
		return Response.success();
	}
	
	@DeleteMapping("/user")
	@Permission(code = FunctionCode.USER_INFO_DELETE)
	public Response<Object> deleteUser() {
		log.info("Received user delete request, userInfo:{}", LoginUserHolder.get());
		return Response.success();
	}
}
