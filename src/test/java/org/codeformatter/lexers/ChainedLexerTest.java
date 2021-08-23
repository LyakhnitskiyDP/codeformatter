package org.codeformatter.lexers;

import static org.assertj.core.api.Assertions.assertThat;

import org.codeformatter.io.Reader;
import org.codeformatter.io.file.FileReader;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.tokenizers.ForLoopTokenizer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ChainedLexerTest {


    public void setup() {

    }

    @Test
    public void should_distinguish_for_loop_statement() {

        Reader reader = new StringReader("""
                for (int i = 0; i < size; i++) {
                    doSomething();
                }""");
        ChainedLexer chainedLexer = new ChainedLexer(reader);

        List<Token> tokens = new ArrayList<>();
        while (chainedLexer.hasMoreTokens()) {
            tokens.add(chainedLexer.readToken());
        }

        assertThat(tokens).anyMatch(token -> token.getName().equals(ForLoopTokenizer.LEXEME_TYPE));
    }

    @Test
    public void should_not_consume_variable_named_forks() {

        Reader reader = new StringReader("""
                doSomething();
                int forks = 123;""");

        ChainedLexer chainedLexer = new ChainedLexer(reader);

        List<Token> lexemes = new ArrayList<>();
        while (chainedLexer.hasMoreTokens()) {
            lexemes.add(chainedLexer.readToken());
        }

        lexemes.forEach(System.out::println);

        assertThat(lexemes).noneMatch(
                token -> token.getName().equals(ForLoopTokenizer.LEXEME_TYPE)
        );
    }


}

