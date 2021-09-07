package org.codeformatter.utils;

public class StringUtil {

    public static Character getFirstCharOrNull(String str) {

        if (str == null) {
            return null;
        }

        if (str.equals("\\n")) {
            return '\n';
        }

        if (str.equals("\\r")) {
            return '\r';
        }

        return str.charAt(0);
    }

}
