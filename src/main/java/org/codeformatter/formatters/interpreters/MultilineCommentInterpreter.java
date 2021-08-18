package org.codeformatter.formatters.interpreters;

import org.codeformatter.formatters.Context;

import java.util.Queue;

public class MultilineCommentInterpreter extends Interpreter {

    public MultilineCommentInterpreter(
            Queue<Character> characterQueue,
            Context context) {

        super(characterQueue, context);
    }

    @Override
    boolean patternIsFound(char ch) {
        return getContent().startsWith("/") || getContent().startsWith("/*");
    }

    @Override
    boolean patternIsComplete() {
        return getContent().endsWith("*/");
    }

}
