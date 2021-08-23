package org.codeformatter.lexers;

import static org.codeformatter.tokenizers.TokenizerType.FOR_LOOP_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.MULTILINE_COMMENT_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.SIMPLE_STATEMENT_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.SINGLE_LINE_COMMENT_TOKENIZER;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.io.Reader;
import org.codeformatter.tokenizers.Tokenizer;
import org.codeformatter.tokenizers.TokenizerFactory;

@Slf4j
@RequiredArgsConstructor
public class ChainedLexer implements Lexer {

    private final Reader reader;
    private Tokenizer[] tokenizers;

    private Deque<Character> charsToInterpret;
    private Queue<Token> foundTokens;

    {
        this.charsToInterpret = new LinkedList<>();
        this.foundTokens = new LinkedList<>();

        TokenizerFactory tokenizerFactory = new TokenizerFactory(charsToInterpret, foundTokens);

        this.tokenizers = new Tokenizer[] {
                tokenizerFactory.getTokenizer(FOR_LOOP_TOKENIZER),
                tokenizerFactory.getTokenizer(MULTILINE_COMMENT_TOKENIZER),
                tokenizerFactory.getTokenizer(SINGLE_LINE_COMMENT_TOKENIZER),
                tokenizerFactory.getTokenizer(SIMPLE_STATEMENT_TOKENIZER)
        };

        for (int i = 0; i < tokenizers.length; i++) {
            log.debug("Using tokenizer for tokens of type: {} with priority of {}",
                      tokenizers[i].getLexemeName(),
                      i + 1);
        }

    }

    @Override
    public boolean hasMoreTokens() {
        return reader.hasMoreChars();
    }

    @Override
    public Token readToken() {

        while (reader.hasMoreChars() && foundTokens.isEmpty()) {

            char ch = reader.readChar();
            charsToInterpret.addLast(ch);

            for (Tokenizer tokenizer : tokenizers) {
                tokenizer.consumeCharactersFromQueue();
            }
        }

        return foundTokens.poll();
    }

}
