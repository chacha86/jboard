package com.jboard.exception;

public class NotImplementsResourceMethodException extends Exception {
    private final String msg;
    public NotImplementsResourceMethodException(String msg) {
       this.msg = msg;
    }
    @Override
    public String getMessage() {
        return msg;
    }
}
