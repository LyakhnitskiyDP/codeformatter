package org.codeformatter.formatters.interpreters;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ForLoopInterpreterTest {

    ForLoopInterpreter forLoopInterpreter;

    @BeforeEach
    public void setup() {
        forLoopInterpreter = new ForLoopInterpreter(null, null);
    }

    @ParameterizedTest
    @ValueSource(strings = { "f", "fo", "for", "for ", "for (", "for (int i = 0; i < size; i++)"})
    public void should_recognize_patterns(String content) {

        forLoopInterpreter.setContent(new StringBuilder(content));

        assertTrue(forLoopInterpreter.patternIsFound());
    }

    @ParameterizedTest
    @ValueSource(strings = { "x", "fx", "fox", "forx", "for x", "for {int i = 0; i < size; i++)"})
    public void should_not_recognize_patterns(String content) {

        forLoopInterpreter.setContent(new StringBuilder(content));

        assertFalse(forLoopInterpreter.patternIsFound());
    }

    @Test
    public void should_recognize_completion_of_a_pattern() {

        String completedPattern = "for (int i = 0; i < size; i++) {";

        forLoopInterpreter.setContent(new StringBuilder(completedPattern));

        assertTrue(forLoopInterpreter.patternIsComplete());
    }

    @Test
    public void should_recognize_uncompleted_patter() {
        String completedPattern = "for (int i = 0; i < size";

        forLoopInterpreter.setContent(new StringBuilder(completedPattern));

        assertFalse(forLoopInterpreter.patternIsComplete());
    }

}
