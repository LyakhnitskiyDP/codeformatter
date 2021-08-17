package org.codeformatter.exceptions;

public class ReaderException extends RuntimeException {

    public ReaderException(String message, Throwable cause) {

        super(message, cause);
    }

    public ReaderException(String message) {

        super(message);
    }

}
