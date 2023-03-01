package com.zakiis.security.demo.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.zakiis.security.demo.domain.constants.FunctionCode;
import com.zakiis.security.demo.domain.dto.user.UserInfo;

@Service
public class FakeUserService {

	public UserInfo selectByUsername(String username) {
		UserInfo mockedUser = getMockedUser();
		if (mockedUser.getUsername().equals(username)) {
			return mockedUser;
		}
		return null;
	}
	
	private UserInfo getMockedUser() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(1L);
		userInfo.setUsername("zhangsan");
		userInfo.setPassword("123456");
		userInfo.setRealName("张三");
		userInfo.setFunctions(Set.of(FunctionCode.USER_INFO_QUERY, FunctionCode.USER_INFO_MODIFY));
		return userInfo;
	}
}
