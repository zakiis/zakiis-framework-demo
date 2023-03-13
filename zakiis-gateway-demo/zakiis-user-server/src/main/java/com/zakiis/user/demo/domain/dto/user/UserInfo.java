package com.zakiis.user.demo.domain.dto.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 8486974126057483631L;
	
	private Long userId;
	private String username;
	@JsonIgnore
	private String password;
	private String realName;

}
