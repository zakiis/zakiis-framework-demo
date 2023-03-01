package com.zakiis.security.demo.service.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.core.util.JsonUtil;
import com.zakiis.security.demo.domain.constants.SecurityConstant;
import com.zakiis.security.demo.domain.dto.user.UserInfo;
import com.zakiis.security.demo.service.holder.LoginUserHolder;
import com.zakiis.security.jwt.JWTUtil;
import com.zakiis.security.jwt.algorithm.Algorithm;
import com.zakiis.security.jwt.interfaces.DecodedJwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginUserFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(SecurityConstant.TOKEN_HEADER_NAME);
		if (StringUtils.isBlank(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJwt decodedJwt = JWTUtil.decode(token);
		if (!"HS256".equals(decodedJwt.getAlgorithm())) {
			throw new ZakiisRuntimeException("demo project not support cipher method that not equals HS256");
		}
		JWTUtil.require(Algorithm.HMAC256(SecurityConstant.HMAC_SHA_256_KEY)).verify(decodedJwt);
		UserInfo userInfo = JsonUtil.toObject(decodedJwt.getClaim("userInfo"), UserInfo.class);
		try {
			LoginUserHolder.set(userInfo);
			filterChain.doFilter(request, response);
		} finally {
			LoginUserHolder.clear();
		}
	}

}
