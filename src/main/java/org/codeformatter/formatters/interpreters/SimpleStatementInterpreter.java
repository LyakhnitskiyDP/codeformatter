package org.codeformatter.formatters.interpreters;

import java.util.Arrays;
import java.util.Queue;
import org.codeformatter.formatters.Formatter;

public class SimpleStatementInterpreter extends Interpreter {

    private static final String[] lineTerminatingChars = { ";", "{", "}" };

    public SimpleStatementInterpreter(
            Queue<Character> characterQueue,
            Formatter.Context context) {

        super(characterQueue, context);
    }


    @Override
    boolean patternIsFound(char ch) {
        return true;
    }

    @Override
    boolean patternIsComplete() {

        return Arrays.stream(lineTerminatingChars)
                     .anyMatch(terminatingChar -> getContent().endsWith(terminatingChar));

    }
}
