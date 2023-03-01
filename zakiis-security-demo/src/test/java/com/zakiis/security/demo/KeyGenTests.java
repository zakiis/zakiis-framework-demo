package com.zakiis.security.demo;

import org.junit.jupiter.api.Test;

import com.zakiis.security.HMACUtil;
import com.zakiis.security.codec.HexUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyGenTests {
	
	@Test
	public void genHmacSha256Key() {
		byte[] secretBytes = HMACUtil.genSecretKey(HMACUtil.HMACType.HMAC_SHA_256);
		log.info("generate key:{}", HexUtil.toHexString(secretBytes));
	}

}
