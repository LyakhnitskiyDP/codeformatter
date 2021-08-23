package org.codeformatter.tokenizers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MultilineCommentTokenizerTest {

    private MultilineCommentTokenizer multilineCommentTokenizer;

    @BeforeEach
    public void setup() {

        this.multilineCommentTokenizer = new MultilineCommentTokenizer(null, null);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/*", "/* some text", "/* a complete comment */"})
    public void should_recognize_pattern(String pattern) {

        multilineCommentTokenizer.setCurrentLexeme(new StringBuilder(pattern));

        assertTrue(multilineCommentTokenizer.patternIsFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/* COMMENT */", "/**/", "/* */"})
    public void should_recognize_pattern_completion(String pattern) {

        multilineCommentTokenizer.setCurrentLexeme(new StringBuilder(pattern));

        assertTrue(multilineCommentTokenizer.patternIsComplete());
    }

}
