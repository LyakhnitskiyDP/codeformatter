package org.codeformatter.io.string;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
import org.codeformatter.exceptions.ReaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StringReaderTest {

    private StringReader stringReader;
    private String content;

    @BeforeEach
    public void setup() {

        this.content = "content";
        this.stringReader = new StringReader(content);
    }

    @Test
    public void should_read_all_characters() {

        StringBuilder stringBuilder = new StringBuilder();

        while (stringReader.hasMoreChars()) {

            stringBuilder.append(stringReader.readChar());
        }

        assertThat(stringBuilder.toString()).isEqualTo(content);
    }

    @Test
    public void should_throw_reader_exception_when_there_is_nothing_to_read() {

        assertThatExceptionOfType(ReaderException.class)
                  .isThrownBy(() -> {
                      while (true) {
                          stringReader.readChar();
                      }
                  });

    }

}
