package org.codeformatter.formatters.interpreters;

import java.util.Arrays;
import java.util.Queue;
import org.codeformatter.formatters.Context;

public class SimpleStatementInterpreter extends Interpreter {

    private static final String[] lineTerminatingChars = { ";", "{", "}" };

    public SimpleStatementInterpreter(
            Queue<Character> characterQueue,
            Context context) {

        super(characterQueue, context);
    }


    @Override
    boolean patternIsFound() {
        return true;
    }

    @Override
    boolean patternIsComplete() {

        return Arrays.stream(lineTerminatingChars)
                     .anyMatch(terminatingChar -> getContent().endsWith(terminatingChar));

    }
}
