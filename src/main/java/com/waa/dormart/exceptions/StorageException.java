package com.waa.dormart.exceptions;

import org.springframework.http.HttpStatus;

public class StorageException extends HttpException {
    public StorageException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public StorageException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
