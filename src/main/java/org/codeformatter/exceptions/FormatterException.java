package org.codeformatter.exceptions;


public class FormatterException extends Exception {

    public FormatterException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FormatterException(String msg) {
        super(msg);
    }

    public FormatterException() {

    }
}
