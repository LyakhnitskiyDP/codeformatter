package org.codeformatter.tokenizers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SingleLineCommentTokenizerTest {

    private SingleLineCommentTokenizer singleLineCommentTokenizer;

    @BeforeEach
    public void setup() {

        singleLineCommentTokenizer = new SingleLineCommentTokenizer(null, null);
    }

    @ParameterizedTest
    @ValueSource(strings = { "/", "//", "//just a comment here"})
    public void should_recognize_pattern(String pattern) {

        singleLineCommentTokenizer.setCurrentLexeme(new StringBuilder(pattern));

        assertTrue(singleLineCommentTokenizer.patternIsFound());
    }

    @Test
    public void should_recognize_completed_pattern() {

        String completedPattern = "//comment" + System.lineSeparator();

        singleLineCommentTokenizer.setCurrentLexeme(new StringBuilder(completedPattern));

        assertTrue(singleLineCommentTokenizer.patternIsComplete());
    }

}
