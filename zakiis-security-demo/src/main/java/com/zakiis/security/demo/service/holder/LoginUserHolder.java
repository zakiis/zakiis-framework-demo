package com.zakiis.security.demo.service.holder;

import com.zakiis.core.ThreadContext;
import com.zakiis.security.demo.domain.dto.user.UserInfo;

public class LoginUserHolder {

	private final static ThreadContext context = new ThreadContext();
	private final static String KEY = "loginUser";
	
	public static void set(UserInfo userInfo) {
		context.put(KEY, userInfo);
	}
	
	public static UserInfo get() {
		return context.get(KEY, UserInfo.class);
	}
	
	public static void clear() {
		context.clear();
	}
}
