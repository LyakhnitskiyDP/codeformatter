package org.codeformatter.lexers.tokenizers;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import org.codeformatter.lexers.Lexeme;


public class SimpleStatementTokenizer extends Tokenizer {

    private static final String LEXEME_TYPE = "Simple statement";

    private static final String[] lineTerminatingChars = { ";", "{", "}" };

    public SimpleStatementTokenizer(
            Deque<Character> characterQueue,
            List<Lexeme> lexemes) {

        super(characterQueue, lexemes);
    }


    @Override
    boolean patternIsFound() {
        return true;
    }

    @Override
    void appendFoundLexeme() {
        getLexemes().add(new Lexeme(LEXEME_TYPE, getCurrentToken()));
    }

    @Override
    boolean patternIsComplete() {

        return Arrays.stream(lineTerminatingChars)
                     .anyMatch(terminatingChar -> getCurrentToken().endsWith(terminatingChar));

    }
}
