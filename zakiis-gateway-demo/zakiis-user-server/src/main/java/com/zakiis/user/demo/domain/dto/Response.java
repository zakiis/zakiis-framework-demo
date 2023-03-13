package com.zakiis.user.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zakiis.security.RateLimitResponse;
import com.zakiis.user.demo.domain.constants.Responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response<T> implements RateLimitResponse {

	private static final long serialVersionUID = -512337377817145407L;
	
	private String code;
	private String msg;
	private T data;
	
	public static <T> Response<T> of(Responses responses, T data) {
		Response<T> response = new Response<T>();
		response.setCode(responses.getCode());
		response.setMsg(responses.getMessage());
		response.setData(data);
		return response;
	}

	public static Response<Object> success() {
		return Response.of(Responses.SUCCESS, null);
	}
	
	public static <T> Response<T> success(T data) {
		return Response.of(Responses.SUCCESS, data);
	}
	
	public static Response<Object> fail() {
		return Response.of(Responses.UNKNOWN_ERROR, null);
	}
	
	public static <T> Response<T> fail(T data) {
		return Response.of(Responses.UNKNOWN_ERROR, data);
	}

	@Override
	@JsonIgnore
	public boolean isSuccess() {
		return Responses.SUCCESS.getCode().equals(code);
	}
}
