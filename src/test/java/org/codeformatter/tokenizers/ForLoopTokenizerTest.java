package org.codeformatter.tokenizers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ForLoopTokenizerTest {

    private ForLoopTokenizer forLoopTokenizer;

    @BeforeEach
    public void setup() {

        this.forLoopTokenizer = new ForLoopTokenizer(null, null);
    }

    @ParameterizedTest
    @ValueSource(strings = {"f", "fo", "for", "for ", "for (", "for (i", "for (int i = 0;"})
    public void pattern_should_be_recognized(String pattern) {

        forLoopTokenizer.setCurrentLexeme(new StringBuilder(pattern));

        assertTrue(forLoopTokenizer.patternIsFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "for (int i = 0; i < size; i++) {",
            "for (;;) {",
            "for (int i = 0; true;) {"
    })
    public void should_recognize_pattern_completion(String pattern) {

        forLoopTokenizer.setCurrentLexeme(new StringBuilder(pattern));

        assertTrue(forLoopTokenizer.patternIsComplete());
    }

}
