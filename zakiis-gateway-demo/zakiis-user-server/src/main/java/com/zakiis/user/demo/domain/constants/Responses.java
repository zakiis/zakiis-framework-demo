package com.zakiis.user.demo.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Responses {

	UNAUTHORIZED("401", "unauthrozied"),
	SUCCESS("000000", "success"),
	PARAMETER_INVALID("102000", "parameter invalid"),
	RUNTIME_ERROR("103000", "runtime error"),
	UNKNOWN_ERROR("999999", "unknown error")
	;
	private final String code;
	private final String message;
}
