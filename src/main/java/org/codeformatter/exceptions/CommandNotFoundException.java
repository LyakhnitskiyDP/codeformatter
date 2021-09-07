package org.codeformatter.exceptions;

public class CommandNotFoundException extends ExternalizedConfigException {

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
