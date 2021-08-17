package org.codeformatter.formatters.interpreters;

import java.util.Queue;
import org.codeformatter.formatters.Formatter;

public class IfStatementInterpreter extends Interpreter {


    public IfStatementInterpreter(
            Queue<Character> characterQueue,
            Formatter.Context context) {

        super(characterQueue, context);
    }

    @Override
    boolean patternIsFound(char ch) {

        //for (a; b; c) {
        boolean forLoopStarting = "for (".startsWith(getContent());

        return forLoopStarting;
    }

    @Override
    boolean patternIsComplete() {

        String content = getContent();

        boolean hasParenthesis = content.contains("(") && content.contains(")");
        boolean hasBracket = content.contains("{");

        return hasParenthesis && hasBracket && hasTwoSemicolumns();
    }

    private boolean hasTwoSemicolumns() {

        int numberOfSemicolumns = 0;

        for (char ch : getContent().toCharArray()) {
            if (String.valueOf(ch).equals(";")) {
                numberOfSemicolumns++;
            }
        }

        return numberOfSemicolumns == 2;
    }
}
