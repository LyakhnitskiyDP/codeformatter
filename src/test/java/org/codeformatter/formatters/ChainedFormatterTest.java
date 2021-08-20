package org.codeformatter.formatters;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.io.string.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ChainedFormatterTest {

    private ChainedFormatter chainedFormatter;

    @BeforeEach
    public void setUp() {

        chainedFormatter = new ChainedFormatter();
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

    @Test
    public void should_format_multiline_comments() {

        String codeToFormat = "aaa bbb { ccc; /* MY COMMENT HERE */ ddd; eee; z (x) { y; } }";

        String expectedCode = """
                aaa bbb {
                    ccc;
                    /* MY COMMENT HERE */
                    ddd;
                    eee;
                    z (x) {
                        y;
                    }
                }
                """;

        testFormatter(codeToFormat, expectedCode);
    }

    private void testFormatter(String codeToFormat, String expectedCode) {

        Reader reader = new StringReader(codeToFormat);

        StringBuilder resultStringBuilder = new StringBuilder();
        Writer writer = new StringWriter(resultStringBuilder);

        chainedFormatter.format(reader, writer);

        assertThat(resultStringBuilder.toString())
                  .isEqualToNormalizingNewlines(expectedCode);
    }

    @Test
    public void should_throw_exception_on_negative_block_level() {

        assertThatIllegalArgumentException()
                  .isThrownBy(() -> new Context(null, -2));

        assertThatIllegalStateException()
                  .isThrownBy(() -> {
                      Context ctx = new Context(null, 0);
                      ctx.decreaseBlockLevel();
                  });

    }

}
