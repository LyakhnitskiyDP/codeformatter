package org.codeformatter.formatters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LoopCodeFormatterTest {

    private static LoopCodeFormatter formatter;

    @BeforeAll
    public static void setUp() {

        formatter = new LoopCodeFormatter();
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

        test_formatter(codeToFormat, expectedCode);
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

        test_formatter(codeToFormat, expectedCode);
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

        test_formatter(codeToFormat, expectedCode);
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

        test_formatter(codeToFormat, expectedCode);
    }

    private void test_formatter(String codeToFormat, String expectedCode) {

        String formattedCode = formatter.formatCode(codeToFormat);

        Assertions.assertThat(formattedCode)
                .isEqualToNormalizingNewlines(expectedCode);
    }

}
