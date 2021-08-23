package org.codeformatter.tokenizers;

import java.util.Deque;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.codeformatter.lexers.Token;

@RequiredArgsConstructor
public class TokenizerFactory {

    private final Deque<Character> characterQueue;
    private final Queue<Token> tokens;

    public Tokenizer getTokenizer(TokenizerType type) {

        return switch (type) {
            case FOR_LOOP_TOKENIZER -> new ForLoopTokenizer(characterQueue, tokens);
            case MULTILINE_COMMENT_TOKENIZER -> new MultilineCommentTokenizer(characterQueue, tokens);
            case SIMPLE_STATEMENT_TOKENIZER -> new SimpleStatementTokenizer(characterQueue, tokens);
            case SINGLE_LINE_COMMENT_TOKENIZER -> new SingleLineCommentTokenizer(characterQueue, tokens);
        };
    }

}
