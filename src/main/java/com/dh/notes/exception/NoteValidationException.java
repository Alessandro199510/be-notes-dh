package com.dh.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoteValidationException extends RuntimeException {

    public NoteValidationException(String message) {
        super(message);
    }

    public NoteValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}