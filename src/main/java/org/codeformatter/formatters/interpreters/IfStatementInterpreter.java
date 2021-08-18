package org.codeformatter.formatters.interpreters;

import java.util.Queue;
import org.codeformatter.formatters.Context;


public class IfStatementInterpreter extends Interpreter {

    public IfStatementInterpreter(
            Queue<Character> characterQueue,
            Context context) {

        super(characterQueue, context);
    }

    @Override
    boolean patternIsFound(char ch) {


        return getContent().contains("f") && !getContent().contains("f ");
        /*
        boolean forLoopStarting = "for (".startsWith(getContent());

        return forLoopStarting;

         */
    }

    @Override
    boolean patternIsComplete() {

        String content = getContent();

        //boolean hasParenthesis = content.contains("(") && content.contains(")");
        boolean hasBracket = content.contains("{");

        return hasBracket;
    }

    private boolean hasTwoSemicolons() {

        int numberOfSemicolons = 0;

        for (char ch : getContent().toCharArray()) {
            if (String.valueOf(ch).equals(";")) {
                numberOfSemicolons++;
            }
        }

        return numberOfSemicolons == 2;
    }
}
