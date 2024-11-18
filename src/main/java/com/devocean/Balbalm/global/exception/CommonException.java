package com.devocean.Balbalm.global.exception;

import com.devocean.Balbalm.global.enumeration.ResultCode;
import lombok.Getter;

import java.io.Serial;

@Getter
public class CommonException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6071663301091257969L;
    private final ResultCode code;

    public CommonException(ResultCode code) {
        this.code = code;
    }

    public CommonException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(ResultCode code, Throwable t) {
        super(code.getMessage(), t);
        this.code = code;
    }

    public CommonException(ResultCode code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }
}
