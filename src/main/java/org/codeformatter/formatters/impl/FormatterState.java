package org.codeformatter.formatters.impl;

import lombok.Value;

@Value(staticConstructor = "of")
public class FormatterState {

    public static final String INITIAL = "initial";
    public static final String WRITING_LINE = "writing line";
    public static final String IGNORING_WHITESPACES = "ignoring whitespaces";

    public static final String WRITING_STRING_LITERAL = "writing string literal";

    public static final String MULTILINE_COMMENT_START_1 = "Start of a multiline comment (/)";
    public static final String MULTILINE_COMMENT_START_2 = "Start of a multiline comment (/*)";
    public static final String WRITING_MULTILINE_COMMENT = "writing multiline comment";
    public static final String MULTILINE_COMMENT_END_1 = "End of a multiline comment (*)";
    public static final String MULTILINE_COMMENT_END_2 = "End of a multiline comment (*/)";

    public static final String TERMINATED = "terminated";

    String state;

}
