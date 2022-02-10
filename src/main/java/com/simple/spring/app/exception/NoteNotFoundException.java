package com.simple.spring.app.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(long id) {
        super(String.format("Note with ID '%d' not found!", id));
    }
}