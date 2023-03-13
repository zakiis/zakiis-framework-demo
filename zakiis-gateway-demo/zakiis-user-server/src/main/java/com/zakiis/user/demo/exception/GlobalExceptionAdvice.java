package com.zakiis.user.demo.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zakiis.core.exception.ZakiisRuntimeException;
import com.zakiis.user.demo.domain.constants.Responses;
import com.zakiis.user.demo.domain.dto.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public Response<?> handleThrowable(Throwable e) {
		log.error("got an unknown error", e);
		String msg = Optional.ofNullable(e.getMessage()).orElse(Responses.UNKNOWN_ERROR.getMessage());
		Response<Object> response = new Response<>();
		response.setCode(Responses.UNKNOWN_ERROR.getCode());
		response.setMsg(msg);
		return response;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ZakiisRuntimeException.class)
	public Response<?> handleThrowable(ZakiisRuntimeException e) {
		String msg = Optional.ofNullable(e.getMessage()).orElse(Responses.RUNTIME_ERROR.getMessage());
    	log.warn("{}, at:\n\t{}", msg, buildSimpleStatckTraceMsg(e));
		Response<Object> response = new Response<>();
		response.setCode(Responses.RUNTIME_ERROR.getCode());
		response.setMsg(msg);
		return response;
	}
	
	private String buildSimpleStatckTraceMsg(Throwable e) {
    	if (e.getStackTrace().length > 3) {
    		List<StackTraceElement> stacks = Arrays.asList(e.getStackTrace()[0], e.getStackTrace()[1], e.getStackTrace()[2]);
    		return StringUtils.join(stacks, "\n\t");
    	}
    	return StringUtils.join(e.getStackTrace(), "\n\t");
    }
}
