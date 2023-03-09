package com.zakiis.rdb.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zakiis.security.annotation.Cipher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Table name: address
 * 2022-03-29 10:41:43
 */
@Getter
@Setter
@ToString
@TableName("address")
public class Address {

	private Long id;

    private String country;

    private String province;

    private String city;

    @Cipher
    private String region;
    @Cipher
    private String street;
    @Cipher(length = 6)
    private String zipCode;
    @Cipher
    private String details;
}
