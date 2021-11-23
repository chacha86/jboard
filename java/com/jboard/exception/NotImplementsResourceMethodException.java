package com.jboard.exception;

public class NotImplementsResourceMethodException extends Exception {
    @Override
    public String getMessage() {
        return "not implements resource methods";
    }
}
