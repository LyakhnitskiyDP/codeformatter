package org.codeformatter.exceptions;

public class CloseException extends RuntimeException {

    public CloseException(String msg) {
        super(msg);
    }

    public CloseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CloseException() {

    }

}
