package com.pb.test.exception;

import org.springframework.http.HttpStatus;

public class ZeroLikesException extends GlobalException {
    public ZeroLikesException() {
        super(HttpStatus.BAD_REQUEST,"This note already has 0 likes!");
    }
}
