package com.zakiis.rdb.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zakiis.security.annotation.Cipher;

import lombok.Getter;
import lombok.Setter;

/**
 * Table name: user
 * 2022-03-29 10:41:43
 */
@Getter
@Setter
@TableName("user")
public class User {

	private Long id;
	private String username;
	@Cipher
	private String password;
	private String name;
	/** residence address reference table address */
	private Long resAddressId;
}
