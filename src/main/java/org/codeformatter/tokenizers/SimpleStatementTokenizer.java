package org.codeformatter.tokenizers;

import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;
import org.codeformatter.lexers.Token;


public class SimpleStatementTokenizer extends Tokenizer {

    public static final String LEXEME_TYPE = "Simple statement";

    private static final String[] lineTerminatingChars = { ";", "{", "}" };

    public SimpleStatementTokenizer(
            Deque<Character> characterQueue,
            Queue<Token> completedTokens) {

        super(characterQueue, completedTokens);
    }


    @Override
    boolean patternIsFound() {
        return true;
    }

    @Override
    public String getLexemeName() {
        return LEXEME_TYPE;
    }

    @Override
    boolean patternIsComplete() {

        return Arrays.stream(lineTerminatingChars)
                     .anyMatch(terminatingChar -> getCurrentLexeme().endsWith(terminatingChar));

    }
}
