package org.codeformatter.lexers;

import static org.assertj.core.api.Assertions.assertThat;
import org.codeformatter.io.Reader;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.lexers.tokenizers.ForLoopTokenizer;
import org.codeformatter.lexers.tokenizers.MultilineCommentTokenizer;
import org.codeformatter.lexers.tokenizers.SimpleStatementTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ChainedLexerTest {

    private ChainedLexer chainedLexer;

    @BeforeEach
    public void setup() {

        chainedLexer = new ChainedLexer();
    }

    @Test
    public void should_distinguish_for_loop_statement() {

        Reader reader = new StringReader("""
                for (int i = 0; i < size; i++) {
                    doSomething();
                }
                """);

        Lexeme expectedLexeme =
                new Lexeme(ForLoopTokenizer.LEXEME_TYPE, "for (int i = 0; i < size; i++) {");

        List<Lexeme> lexemes = chainedLexer.getLexemes(reader);

        assertThat(lexemes).contains(expectedLexeme);
    }

    @Test
    public void should_not_consume_variable_named_forks() {

        Reader reader = new StringReader("""
                doSomething();
                int forks = 123;
                """);

        List<Lexeme> lexemes = chainedLexer.getLexemes(reader);

        assertThat(lexemes).noneMatch(
                lexeme -> lexeme.type().equals(ForLoopTokenizer.LEXEME_TYPE)
        );
    }

    @Test
    public void should_distinguish_multiline_comments() {

        Reader reader = new StringReader("""
                /* first line 
                second line */
                if (something) { somethingElse(); }
                """);

        Lexeme expectedLexeme =
                new Lexeme(
                        MultilineCommentTokenizer.LEXEME_TYPE,
                        """
                                /* first line
                                      second line */
                                """);

        List<Lexeme> lexemes = chainedLexer.getLexemes(reader);

        assertThat(lexemes).anyMatch(
                lexeme -> lexeme.type().equals(MultilineCommentTokenizer.LEXEME_TYPE)
        );
    }

    @Test
    public void should_distinguish_simple_statements() {

        Reader reader = new StringReader("""
                boolean a = true;
                int b = 123;
                if (a && b < 15) {
                    doSomething(); 
                }
                """);

        List<Lexeme> lexemes = chainedLexer.getLexemes(reader);

        assertThat(lexemes.size()).isEqualTo(5);

        assertThat(lexemes).allMatch(
                lexeme -> lexeme.type().equals(SimpleStatementTokenizer.LEXEME_TYPE)
        );
    }

}
