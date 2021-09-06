package org.codeformatter.lexers.impl;

import lombok.Value;

@Value(staticConstructor = "of")
public class LexerState {

    public static final String INITIAL = "initial";
    public static final String TERMINATED = "terminated";
    public static final String FOR_1 = "for_1";
    public static final String FOR_2 = "for_2";
    public static final String FOR_3 = "for_3";
    public static final String FOR = "for";

    public static final String MULTILINE_COMMENT_START1 = "multiline comment start 1";
    public static final String MULTILINE_COMMENT_START2 = "multiline comment start 2";
    public static final String MULTILINE_COMMENT = "multiline comment";
    public static final String MULTILINE_COMMENT_END1 = "multiline comment end 1";
    public static final String MULTILINE_COMMENT_END2 = "multiline comment end 2";

    String state;

}
