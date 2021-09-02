package org.codeformatter.utils;

public class LoggingUtil {

    public static String printChar(char ch) {

        return switch (ch) {
            case ('\n') -> "\\n";
            case (' ') -> "' '";
            case ('\r') -> "\\r";
            default -> "'" + String.valueOf(ch) + "'";
        };

    }

}
