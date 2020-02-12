package com.jumkid.oauthcentral.exception;

public class DuplicateValueException extends RuntimeException {
    private static final String ERROR = "Duplicate data value is found.";

    public DuplicateValueException() { super(ERROR); }

    public DuplicateValueException(String message) { super(message); }
}
