package org.codeformatter.lexers.tokenizers;

import java.util.Deque;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.codeformatter.lexers.Lexeme;

@RequiredArgsConstructor
public class TokenizerFactory {

    private final Deque<Character> characterQueue;
    private final List<Lexeme> lexemes;

    public Tokenizer getTokenizer(TokenizerType type) {

        return switch (type) {
            case FOR_LOOP_TOKENIZER -> new ForLoopTokenizer(characterQueue, lexemes);
            case MULTILINE_COMMENT_TOKENIZER -> new MultilineCommentTokenizer(characterQueue, lexemes);
            case SIMPLE_STATEMENT_TOKENIZER -> new SimpleStatementTokenizer(characterQueue, lexemes);
            case SINGLE_LINE_COMMENT_TOKENIZER -> new SingleLineCommentTokenizer(characterQueue, lexemes);
        };
    }

    public enum TokenizerType {
        FOR_LOOP_TOKENIZER,
        MULTILINE_COMMENT_TOKENIZER,
        SIMPLE_STATEMENT_TOKENIZER,
        SINGLE_LINE_COMMENT_TOKENIZER
    }

}
