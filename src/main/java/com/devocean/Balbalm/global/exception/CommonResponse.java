package com.devocean.Balbalm.global.exception;

import com.devocean.Balbalm.global.enumeration.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
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

	public static <T> CommonResponse<T> success(T result) {
		return CommonResponse.<T>builder()
			.isSuccess(ErrorCode.SUCCESS.isSuccess())
			.code(ResultCode.OK.getCode())
			.message(ResultCode.OK.getMessage())
			.result(result)
			.build();
	}

	public static <T> CommonResponse<T> success() {
		return CommonResponse.<T>builder()
			.isSuccess(ErrorCode.SUCCESS.isSuccess())
			.code(ResultCode.OK.getCode())
			.message(ResultCode.OK.getMessage())
			.build();
	}

	public CommonResponse(ErrorCode errorCode) {
		this.isSuccess = errorCode.isSuccess();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public static <T> CommonResponse<T> fail(ResultCode resultCode) {
		return CommonResponse.<T>builder()
				.isSuccess(ErrorCode.BAD_REQUEST.isSuccess())
				.code(resultCode.getCode())
				.message(resultCode.getMessage())
				.build();
	}

	@Builder
	public CommonResponse(Boolean isSuccess, int code, String message, T result) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
		this.result = result;
	}
}