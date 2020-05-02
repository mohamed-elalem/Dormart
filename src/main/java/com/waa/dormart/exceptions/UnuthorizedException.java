package com.waa.dormart.exceptions;

import org.springframework.http.HttpStatus;

public class UnuthorizedException extends HttpException {
    public UnuthorizedException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public UnuthorizedException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
