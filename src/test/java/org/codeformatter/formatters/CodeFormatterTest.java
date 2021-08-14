package org.codeformatter.formatters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CodeFormatterTest {

    private static CodeFormatter formatter;

    @BeforeAll
    public static void setUp() {

        formatter = new CodeFormatter();
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
                }""";

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
                }""";

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
                }""";

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
                }""";

        test_formatter(codeToFormat, expectedCode);
    }

    private void test_formatter(String codeToFormat, String expectedCode) {

        String formattedCode = formatter.formatCode(codeToFormat);

        Assertions.assertThat(formattedCode)
                  .isEqualToNormalizingNewlines(expectedCode);
    }

    @Nested
    class CodeSplittingTest {

        @Test
        public void should_split_statement_in_two() {

            String codeToSplit = "ooo (ppp) { www; } zzz (xxx) { qqq; }";

            String[] expectedParts = {
                    "ooo (ppp) { www; }",
                    "zzz (xxx) { qqq; }"
            };

            String[] actualParts = formatter.splitStatements(codeToSplit);

            Assertions.assertThat(actualParts).isEqualTo(expectedParts);
        }

        @Test
        public void should_split_deep_blocks() {

            String codeToSplit = "a (b) { c (d) { e; } } f (j) h;";

            String[] expectedParts = {
                    "a (b) { c (d) { e; } }",
                    "f (j) h;"
            };

            String[] actualParts = formatter.splitStatements(codeToSplit);

            Assertions.assertThat(actualParts).isEqualTo(expectedParts);
        }

        @Test
        public void should_split_statement_and_block() {

            String codeToSplit = "aaa; if (something) then { somethingElse; }";

            String[] expectedParts = {
                    "aaa;",
                    "if (something) then { somethingElse; }"
            };

            String[] actualParts = formatter.splitStatements(codeToSplit);

            Assertions.assertThat(actualParts).isEqualTo(expectedParts);
        }

        @Test
        public void should_not_split_one_statement() {

            String code = "aaa;";

            String[] expectedParts = {
                    "aaa;"
            };

            String[] actualParts = formatter.splitStatements(code);

            Assertions.assertThat(actualParts).isEqualTo(expectedParts);
        }

    }

    @Nested
    class BlockParsingTest {

        @Test
        public void should_parse_block_into_3_parts() {

            String blockToParse = "if (something) { something else; }";

            String[] expectedParts = {
                "if (something) {",
                " something else; ",
                "}"
            };

            String[] actualParts = {
                    formatter.getOpeningPartOfBlockStatement(blockToParse),
                    formatter.getInnerPartOfBlockStatement(blockToParse),
                    formatter.getEnclosingPartOfClockStatement(blockToParse)
            };

            Assertions.assertThat(actualParts).isEqualTo(expectedParts);
        }

    }

}
