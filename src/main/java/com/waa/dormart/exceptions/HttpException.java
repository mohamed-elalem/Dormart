package com.waa.dormart.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;
    private String message;

    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
