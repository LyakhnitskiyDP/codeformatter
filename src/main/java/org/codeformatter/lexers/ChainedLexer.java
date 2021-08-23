package org.codeformatter.lexers;

import static org.codeformatter.tokenizers.TokenizerType.FOR_LOOP_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.MULTILINE_COMMENT_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.SIMPLE_STATEMENT_TOKENIZER;
import static org.codeformatter.tokenizers.TokenizerType.SINGLE_LINE_COMMENT_TOKENIZER;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import lombok.RequiredArgsConstructor;
import org.codeformatter.io.Reader;
import org.codeformatter.tokenizers.Tokenizer;
import org.codeformatter.tokenizers.TokenizerFactory;

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
    }

    @Override
    public boolean hasMoreTokens() {
        return reader.hasMoreChars();
    }

    @Override
    public Token readToken() {

        System.out.println("SIZE BEFORE: " + foundTokens.size());
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
