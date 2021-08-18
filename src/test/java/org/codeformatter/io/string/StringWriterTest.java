package org.codeformatter.io.string;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StringWriterTest {

    @Test
    public void should_write_content_to_string_builder() {

        StringBuilder accumulator = new StringBuilder();

        StringWriter stringWriter = new StringWriter(accumulator);

        char[] charsToWrite = { 'a', 'b', 'c' };

        for (char ch : charsToWrite) {
            stringWriter.writeChar(ch);
        }

        assertThat(accumulator.toString().toCharArray())
                  .contains(charsToWrite);
    }

}
