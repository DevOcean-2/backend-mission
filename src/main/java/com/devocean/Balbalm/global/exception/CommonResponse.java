package com.devocean.Balbalm.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
	private Boolean isSuccess;
	private int code;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	public CommonResponse(T result) {
		this.isSuccess = ErrorCode.SUCCESS.isSuccess();
		this.code = ErrorCode.SUCCESS.getCode();
		this.message = ErrorCode.SUCCESS.getMessage();
		this.result = result;
	}

	public CommonResponse(ErrorCode errorCode) {
		this.isSuccess = errorCode.isSuccess();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}
}