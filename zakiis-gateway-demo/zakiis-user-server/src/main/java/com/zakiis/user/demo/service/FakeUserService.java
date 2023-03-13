package com.zakiis.user.demo.service;

import org.springframework.stereotype.Service;

import com.zakiis.user.demo.domain.dto.user.UserInfo;


@Service
public class FakeUserService {

	public UserInfo selectByUsername(String username) {
		UserInfo mockedUser = getMockedUser();
		if (mockedUser.getUsername().equals(username)) {
			return mockedUser;
		}
		return null;
	}
	
	public UserInfo selectByUserId(Long userId) {
		UserInfo mockedUser = getMockedUser();
		if (mockedUser.getUserId().equals(userId)) {
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
		return userInfo;
	}
}
