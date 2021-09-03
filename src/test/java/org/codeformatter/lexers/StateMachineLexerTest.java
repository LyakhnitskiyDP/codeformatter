package org.codeformatter.lexers;

import static org.junit.jupiter.api.Assertions.*;
import static org.codeformatter.tokens.LexicalConstants.*;

import org.codeformatter.io.string.StringReader;
import org.codeformatter.lexers.impl.StateMachineLexer;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StateMachineLexerTest {

    private StateMachineLexer lexer;

    @Test
    public void should_tokenize_complex_structure() {
        String complexStructure = "a; b { c; } /* d { } */ s = \"str\";";

        lexer = new StateMachineLexer(new StringReader(complexStructure));
        Token[] expectedTokens = {
                new DefaultToken(CHAR, "a"),
                new DefaultToken(SEMICOLON, ";"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(CHAR, "b"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(OPENING_CURLY_BRACKET, "{"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(CHAR, "c"),
                new DefaultToken(SEMICOLON, ";"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(CLOSING_CURLY_BRACKET, "}"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(MULTILINE_COMMENT, "/* d { } */"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(CHAR, "s"),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(CHAR, "="),
                new DefaultToken(WHITE_SPACE, " "),
                new DefaultToken(QUOTES, "\""),
                new DefaultToken(CHAR, "s"),
                new DefaultToken(CHAR, "t"),
                new DefaultToken(CHAR, "r"),
                new DefaultToken(QUOTES, "\""),
                new DefaultToken(SEMICOLON, ";")
        };

        for (Token expectedToken : expectedTokens) {
            Token actualToken = lexer.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }

    }

    @Test
    public void should_recognize_whitespace() {


        Token expectedToken = new DefaultToken(WHITE_SPACE, " ");
        Token actualToken = readTokenWithLexer(" ");

        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void should_recognize_opening_curly_bracket() {


        Token expectedToken = new DefaultToken(OPENING_CURLY_BRACKET, "{");
        Token actualToken = readTokenWithLexer("{");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_closing_curly_bracket() {


        Token expectedToken = new DefaultToken(CLOSING_CURLY_BRACKET, "}");
        Token actualToken = readTokenWithLexer("}");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_semicolon() {


        Token expectedToken = new DefaultToken(SEMICOLON, ";");
        Token actualToken = readTokenWithLexer(";");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_line_separator() {


        Token expectedToken = new DefaultToken(LINE_SEPARATOR, "\n");
        Token actualToken = readTokenWithLexer("\n");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_carriage_return() {


        Token expectedToken = new DefaultToken(CARRIAGE_RETURN, "\r");
        Token actualToken = readTokenWithLexer("\r");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_char() {


        Token expectedToken = new DefaultToken(CHAR, "z");
        Token actualToken = readTokenWithLexer("z");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_quotes() {


        Token expectedToken = new DefaultToken(QUOTES, "\"");
        Token actualToken = readTokenWithLexer("\"");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_star() {


        Token expectedToken = new DefaultToken(STAR, "*");
        Token actualToken = readTokenWithLexer("*");

        assertEquals(expectedToken, actualToken);
    }
    @Test
    public void should_recognize_slash() {


        Token expectedToken = new DefaultToken(SLASH, "/");
        Token actualToken = readTokenWithLexer("/");

        assertEquals(expectedToken, actualToken);
    }


    private Token readTokenWithLexer(String input) {

        lexer = new StateMachineLexer(new StringReader(input));

        return lexer.nextToken();
    }

}
