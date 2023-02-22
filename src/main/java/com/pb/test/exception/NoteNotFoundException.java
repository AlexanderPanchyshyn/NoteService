package com.pb.test.exception;

import org.springframework.http.HttpStatus;

public class NoteNotFoundException extends GlobalException {
    public NoteNotFoundException() {
        super(HttpStatus.NOT_FOUND,"No such note with given id!");
    }
}
