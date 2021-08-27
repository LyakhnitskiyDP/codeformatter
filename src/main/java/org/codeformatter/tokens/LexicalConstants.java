package org.codeformatter.tokens;

public class LexicalConstants {
    public static final String OPENING_CURLY_BRACKET = "{";
    public static final String CLOSING_CURLY_BRACKET = "}";
    public static final String SEMICOLON = ";";
    public static final String[] lineTerminatingChars = {
        OPENING_CURLY_BRACKET,
        CLOSING_CURLY_BRACKET,
        SEMICOLON
    };
    public static final String QUOTES = "\"";
    public static final String TRIPLE_QUOTES = "\"\"\"";
    public static final String MULTILINE_COMMENT_START = "/*";
    public static final String MULTILINE_COMMENT_END = "*/";
    public static final String SINGLE_LINE_COMMENT_START = "//";
    public static final String FOR_LOOP_START = "for (";
}
