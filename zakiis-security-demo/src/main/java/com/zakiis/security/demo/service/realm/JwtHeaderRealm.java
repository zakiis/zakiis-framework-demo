package com.zakiis.security.demo.service.realm;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.core.util.JsonUtil;
import com.zakiis.security.Realm;
import com.zakiis.security.demo.domain.constants.SecurityConstant;
import com.zakiis.security.demo.domain.dto.user.UserInfo;
import com.zakiis.security.jwt.JWTUtil;
import com.zakiis.security.jwt.algorithm.Algorithm;
import com.zakiis.security.jwt.interfaces.DecodedJwt;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtHeaderRealm implements Realm {
	
	@Override
	public Set<String> getFunctions(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstant.TOKEN_HEADER_NAME);
		if (StringUtils.isBlank(token)) {
			return Collections.emptySet();
		}
		DecodedJwt decodedJwt = JWTUtil.decode(token);
		if (!"HS256".equals(decodedJwt.getAlgorithm())) {
			throw new ZakiisRuntimeException("demo project not support cipher method that not equals HS256");
		}
		JWTUtil.require(Algorithm.HMAC256(SecurityConstant.HMAC_SHA_256_KEY)).verify(decodedJwt);
		UserInfo userInfo = JsonUtil.toObject(decodedJwt.getClaim("userInfo"), UserInfo.class);
		return userInfo.getFunctions();
	}

}
