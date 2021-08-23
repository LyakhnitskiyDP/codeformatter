package org.codeformatter.tokenizers;

import java.util.Deque;
import java.util.Queue;
import org.codeformatter.lexers.Token;

public class MultilineCommentTokenizer extends Tokenizer {

    public static final String LEXEME_TYPE = "Multiline comment";

    public MultilineCommentTokenizer(
            Deque<Character> characterQueue,
            Queue<Token> completedTokens) {

        super(characterQueue, completedTokens);
    }

    @Override
    boolean patternIsFound() {

        return switch (getCurrentLexeme().length()) {
            case 1 -> getCurrentLexeme().equals("/");
            case 2 -> getCurrentLexeme().equals("/*");
            default -> getCurrentLexeme().startsWith("/*");
        };
    }

    @Override
    boolean patternIsComplete() {

        return getCurrentLexeme().endsWith("*/");
    }

    @Override
    String getLexemeName() {
        return LEXEME_TYPE;
    }

}
