package org.codeformatter.lexers.tokenizers;

import java.util.Deque;
import java.util.List;
import org.codeformatter.lexers.Lexeme;

public class ForLoopTokenizer extends Tokenizer {

    public static final String LEXEME_TYPE = "For loop";

    public ForLoopTokenizer(
            Deque<Character> characterQueue,
            List<Lexeme> lexemes) {

        super(characterQueue, lexemes);
    }

    @Override
    boolean patternIsFound() {

        boolean patternIsFound = switch (getCurrentToken().length()) {
            case 1 -> getCurrentToken().equals("f");
            case 2 -> getCurrentToken().equals("fo");
            case 3 -> getCurrentToken().equals("for");
            case 4 -> getCurrentToken().equals("for ");
            default -> getCurrentToken().startsWith("for (");
        };

        return patternIsFound;
    }

    @Override
    boolean patternIsComplete() {

        return getCurrentToken().endsWith("{");
    }

    @Override
    void appendFoundLexeme() {
        getLexemes().add(new Lexeme(LEXEME_TYPE, getCurrentToken()));
    }

}
