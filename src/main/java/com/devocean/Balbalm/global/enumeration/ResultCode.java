package com.devocean.Balbalm.global.enumeration;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResultCode {

    OK(0, "정상처리"),
    FAIL(-1, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    NOT_FOUND_MISSION(-2, "존재하지 않는 미션 정보입니다."),
    ALREADY_COMPLETE_MISSION(-3, "이미 완료한 미션입니다."),

    UNKNOWN_SERVER_ERROR(-9999, "알수 없는 에러가 발생했습니다.")
    ;

    private final int code;
    private final String message;

    ResultCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCode valueOf(int code) {
        ResultCode resultCode = resolve(code);
        if (resultCode == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return resultCode;
    }

    public static ResultCode resolve(int code) {
        return Arrays.stream(values())
                .filter(resultCode -> resultCode.code == code)
                .findFirst()
                .orElse(null);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
