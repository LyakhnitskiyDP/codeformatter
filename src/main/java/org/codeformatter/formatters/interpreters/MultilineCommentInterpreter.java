package org.codeformatter.formatters.interpreters;

import java.util.Queue;
import org.codeformatter.formatters.Context;

public class MultilineCommentInterpreter extends Interpreter {

    public MultilineCommentInterpreter(
            Queue<Character> characterQueue,
            Context context) {

        super(characterQueue, context);
    }

    @Override
    boolean patternIsFound() {
        return (getContent().length() > 1 ? getContent().startsWith("/*") : getContent().startsWith("/"));
    }

    @Override
    boolean patternIsComplete() {
        return getContent().endsWith("*/");
    }

}
