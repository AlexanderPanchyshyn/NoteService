package com.pb.test.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends GlobalException {
    public UserAlreadyExistsException() {
        super(HttpStatus.NOT_ACCEPTABLE,"User with given username already exists!");
    }
}
