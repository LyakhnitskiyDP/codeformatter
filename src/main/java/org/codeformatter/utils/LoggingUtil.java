package org.codeformatter.utils;

public class LoggingUtil {

    public static String makeCharPrintable(char ch) {

        return switch (ch) {
            case ('\n') -> "\\n";
            case (' ') -> "' '";
            case ('\r') -> "\\r";
            default -> "'" + String.valueOf(ch) + "'";
        };

    }

}
