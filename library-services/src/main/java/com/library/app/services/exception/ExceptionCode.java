package com.library.app.services.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCode {

    SERVER_ERROR(100600),
    BAD_REQUEST(100601),
    UNPROCESSABLE_ENTITY(100603),
    TRANSACTION_ERROR(100604);

    private final int code;

    ExceptionCode(final int code) {
        this.code = code;
    }
    
    @JsonValue
    public int getCode() {
        return code;
    }
}
