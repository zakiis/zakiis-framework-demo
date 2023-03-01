package com.zakiis.security.demo.domain.dto.user;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 6211952391262291667L;
	@NotBlank(message = "username can't be empty")
	private String username;
	@NotBlank(message = "password can't be empty")
	private String password;
	
	public static LoginRequest of(String username, String password) {
		LoginRequest request = new LoginRequest();
		request.setUsername(username);
		request.setPassword(password);
		return request;
	}
}
