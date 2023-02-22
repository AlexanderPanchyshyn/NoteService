package com.pb.test.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND,"No such user with given id!");
    }
}
