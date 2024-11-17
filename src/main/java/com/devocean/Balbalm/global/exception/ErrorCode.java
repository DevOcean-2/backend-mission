package com.devocean.Balbalm.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Success
	SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),

	// Client Error
	BAD_REQUEST(false, HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
	INVALID_MONTH(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 월입니다. 1-12 사이의 값을 입력해주세요."),

	// Server Error
	INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부에서 문제가 발생했습니다.");

	private final boolean isSuccess;
	private final int code;
	private final String message;

	ErrorCode(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}