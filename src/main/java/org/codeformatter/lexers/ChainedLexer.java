package org.codeformatter.lexers;

import static org.codeformatter.lexers.tokenizers.TokenizerFactory.TokenizerType.FOR_LOOP_TOKENIZER;
import static org.codeformatter.lexers.tokenizers.TokenizerFactory.TokenizerType.MULTILINE_COMMENT_TOKENIZER;
import static org.codeformatter.lexers.tokenizers.TokenizerFactory.TokenizerType.SIMPLE_STATEMENT_TOKENIZER;
import static org.codeformatter.lexers.tokenizers.TokenizerFactory.TokenizerType.SINGLE_LINE_COMMENT_TOKENIZER;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.codeformatter.io.Reader;
import org.codeformatter.lexers.tokenizers.Tokenizer;
import org.codeformatter.lexers.tokenizers.TokenizerFactory;


public class ChainedLexer implements Lexer {

    @Override
    public List<Lexeme> getLexemes(Reader reader) {

        Deque<Character> charsToInterpret = new LinkedList<>();
        List<Lexeme> lexemes = new ArrayList<>();

        TokenizerFactory tokenizerFactory = new TokenizerFactory(charsToInterpret, lexemes);

        Tokenizer[] tokenizers = {
                tokenizerFactory.getTokenizer(FOR_LOOP_TOKENIZER),
                tokenizerFactory.getTokenizer(MULTILINE_COMMENT_TOKENIZER),
                tokenizerFactory.getTokenizer(SINGLE_LINE_COMMENT_TOKENIZER),
                tokenizerFactory.getTokenizer(SIMPLE_STATEMENT_TOKENIZER)
        };

        while (reader.hasMoreChars()) {

            char ch = reader.readChar();
            charsToInterpret.addLast(ch);

            for (Tokenizer tokenizer : tokenizers) {
                tokenizer.consumeCharactersFromQueue();
            }
        }

        return lexemes;
    }
}
