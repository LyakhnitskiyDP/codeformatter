package org.codeformatter.formatters.interpreters;

import java.util.Queue;
import org.codeformatter.formatters.Context;


public class ForLoopInterpreter extends Interpreter {

    public ForLoopInterpreter(
            Queue<Character> characterQueue,
            Context context) {

        super(characterQueue, context);
    }

    @Override
    boolean patternIsFound() {

        boolean patterIsFound = switch (getContent().length()) {
            case 1 -> getContent().equals("f");
            case 2 -> getContent().equals("fo");
            case 3 -> getContent().equals("for");
            case 4 -> getContent().equals("for ");
            default -> getContent().startsWith("for (");
        };

        return patterIsFound;
    }

    @Override
    boolean patternIsComplete() {

        return getContent().endsWith("{");
    }

}
