package org.codeformatter.exceptions;

public class ExternalizedConfigException extends RuntimeException {

    public ExternalizedConfigException(String message) {
        super(message);
    }

    public ExternalizedConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
