package org.codeformatter.formatters;

import static org.assertj.core.api.Assertions.assertThat;

import org.codeformatter.formatters.impl.DefaultFormatter;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.io.string.StringWriter;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.impl.StateMachineLexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DefaultFormatterTest {

    private DefaultFormatter formatter;

    @BeforeEach
    public void setUp() {

        formatter = new DefaultFormatter();
    }

    @Test
    public void should_format_string_literals() {

        String codeToFormat = "String myString = \"it's string { still string }\";";

        String expectedCode =
                """
                String myString = "it's string { still string }";
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_multiline_comments() {

        String codeToFormat = "methodCall(); /*Comment { } ;;;*/ anotherMethodCall();";

        String expectedCode =
                """
                methodCall();
                /*Comment { } ;;;*/
                anotherMethodCall();
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_complex_structure() {

        String codeToFormat = "if (blabla == null) { return 1; if (...) { if (...) { something; } } }";

        String expectedCode =
                """
                if (blabla == null) {
                    return 1;
                    if (...) {
                        if (...) {
                            something;
                        }
                    }
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_multiple_inner_statements() {

        String codeToFormat = "if (aaa) { ooo; zzz (xxx) { qqq; } }";

        String expectedCode = """
                if (aaa) {
                    ooo;
                    zzz (xxx) {
                        qqq;
                    }
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_multiple_blocks_on_the_same_level() {
        String codeToFormat = "ooo (ppp) { www; } zzz (xxx) { qqq; }";

        String expectedCode = """
                ooo (ppp) {
                    www;
                }
                zzz (xxx) {
                    qqq;
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_deep_structures() {

        String codeToFormat = "a (b) { c; d (e) { f (j) { z; } } }";

        String expectedCode = """
                a (b) {
                    c;
                    d (e) {
                        f (j) {
                            z;
                        }
                    }
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }


    @Test
    public void should_format_for_loop() {

        String codeToFormat = "a (b) { c; for (int i = 0; i < x; i++) { doSomethingCool(); } }";

        String expectedCode = """
                a (b) {
                    c;
                    for (int i = 0; i < x; i++) {
                        doSomethingCool();
                    } 
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    @Test
    public void should_format_for_loop_and_if_statement_at_the_same_time() {
        String codeToFormat = "a (b) { if (something) { theSomethingElse; } for (int i = 0; i < x; i++) { doSomethingCool(); } }";

        String expectedCode = """
                a (b) {
                    if (something) {
                        theSomethingElse; 
                    }
                    for (int i = 0; i < x; i++) {
                        doSomethingCool();
                    } 
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    private void testFormatter(String codeToFormat, String expectedCode) {

        StringBuilder result = new StringBuilder();
        Writer stringWriter = new StringWriter(result);

        Lexer lexer = new StateMachineLexer(new StringReader(codeToFormat));

        formatter.format(lexer, stringWriter);

        assertThat(
                result.toString()
        ).isEqualToNormalizingNewlines(
                expectedCode
        );
    }

}