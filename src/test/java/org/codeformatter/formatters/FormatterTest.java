package org.codeformatter.formatters;

import org.assertj.core.api.Assertions;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.io.string.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void setUp() {

        formatter = new Formatter();
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

    private void testFormatter(String codeToFormat, String expectedCode) {

        Reader reader = new StringReader(codeToFormat);

        StringBuilder resultStringBuilder = new StringBuilder();
        Writer writer = new StringWriter(resultStringBuilder);

        formatter.format(reader, writer);

        Assertions.assertThat(resultStringBuilder.toString())
                  .isEqualToNormalizingNewlines(expectedCode);
    }

    @Test
    public void should_throw_exception_on_negative_block_level() {

        Assertions.assertThatIllegalArgumentException()
                  .isThrownBy(() -> new Formatter.Context(null, -2));

        Assertions.assertThatIllegalStateException()
                  .isThrownBy(() -> {
                      Formatter.Context ctx = new Formatter.Context(null, 0);
                      ctx.decreaseBlockLevel();
                  });

    }

}