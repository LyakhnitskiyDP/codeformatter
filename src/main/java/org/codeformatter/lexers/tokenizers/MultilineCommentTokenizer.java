package org.codeformatter.lexers.tokenizers;

import java.util.Deque;
import java.util.List;
import org.codeformatter.lexers.Lexeme;

public class MultilineCommentTokenizer extends Tokenizer {

    public static final String LEXEME_TYPE = "Multiline comment";

    public MultilineCommentTokenizer(
            Deque<Character> characterQueue,
            List<Lexeme> lexemes) {

        super(characterQueue, lexemes);
    }

    @Override
    boolean patternIsFound() {

        return switch (getCurrentToken().length()) {
            case 1 -> getCurrentToken().equals("/");
            case 2 -> getCurrentToken().equals("/*");
            default -> getCurrentToken().startsWith("/*");
        };
    }

    @Override
    boolean patternIsComplete() {

        return getCurrentToken().endsWith("*/");
    }

    @Override
    void appendFoundLexeme() {

        getLexemes().add(new Lexeme(LEXEME_TYPE, getCurrentToken()));
    }
}
