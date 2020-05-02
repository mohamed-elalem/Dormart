package com.waa.dormart.exceptions;

import org.springframework.http.HttpStatus;

public class DeleteUnuthorized extends HttpException {
    public DeleteUnuthorized(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public DeleteUnuthorized(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
