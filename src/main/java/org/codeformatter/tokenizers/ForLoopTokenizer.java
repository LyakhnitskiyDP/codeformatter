package org.codeformatter.tokenizers;

import java.util.Deque;
import java.util.Queue;
import org.codeformatter.lexers.Token;
import org.codeformatter.tokenizers.Tokenizer;

public class ForLoopTokenizer extends Tokenizer {

    public static final String LEXEME_TYPE = "For loop";

    public ForLoopTokenizer(
            Deque<Character> characterQueue,
            Queue<Token> completedTokens) {

        super(characterQueue, completedTokens);
    }

    @Override
    boolean patternIsFound() {

        boolean patternIsFound = switch (getCurrentLexeme().length()) {
            case 1 -> getCurrentLexeme().equals("f");
            case 2 -> getCurrentLexeme().equals("fo");
            case 3 -> getCurrentLexeme().equals("for");
            case 4 -> getCurrentLexeme().equals("for ");
            default -> getCurrentLexeme().startsWith("for (");
        };

        return patternIsFound;
    }

    @Override
    boolean patternIsComplete() {

        return getCurrentLexeme().endsWith("{");
    }

    @Override
    public String getLexemeName() {
        return LEXEME_TYPE;
    }

}
