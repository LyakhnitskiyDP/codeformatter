package org.codeformatter.utils;

public class StringUtil {

    public static String trimAndIndent(String content, int indentation) {

        return " ".repeat(indentation) + content.trim();
    }
}
