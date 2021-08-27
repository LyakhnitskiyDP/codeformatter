package org.codeformatter.lexers.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class State {

    private StateName name;

    public enum StateName {
        INITIAL,
        SIMPLE_TEXT,
        FOR_LOOP,
        SINGLE_LINE_COMMENT,
        MULTILINE_COMMENT,
        STRING_LITERAL,
        TEXT_BLOCK,
        TERMINATED
    }
}
