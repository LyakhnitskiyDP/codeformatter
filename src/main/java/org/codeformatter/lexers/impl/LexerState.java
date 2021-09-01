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

    String state;

}
