package com.zakiis.rdb.demo;

import org.junit.jupiter.api.Test;

import com.zakiis.security.AESUtil;
import com.zakiis.security.codec.HexUtil;

public class KeyGenTests {

	@Test
	public void test() {
		String key = HexUtil.toHexString(AESUtil.genKey());
		String iv = HexUtil.toHexString(AESUtil.genIV());
		System.out.println("key: " + key);
		System.out.println("iv: " + iv);
	}
}
