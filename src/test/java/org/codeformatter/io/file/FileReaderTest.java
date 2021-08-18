package org.codeformatter.io.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.codeformatter.exceptions.ReaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FileReaderTest {

    private static final String relativePathToTestFile = "src/test/resources/testFileToRead.txt";

    private FileReader fileReader;

    @BeforeEach
    public void setup() throws FileNotFoundException {

        this.fileReader = new FileReader(Path.of(relativePathToTestFile));
    }

    @Test
    public void should_read_all_chars_from_file() {

        String expectedContent = """
                First line
                Second line
                Third line""";

        StringBuilder readContent = new StringBuilder();

        while (fileReader.hasMoreChars()) {

            readContent.append(fileReader.readChar());
        }

        assertThat(readContent.toString())
                  .isEqualToNormalizingNewlines(expectedContent);

    }

    @Test
    public void should_throw_reader_exception_when_there_is_no_chars_to_read() {


        assertThatExceptionOfType(ReaderException.class)
                  .isThrownBy(() -> {
                      while (true) {
                          fileReader.readChar();
                      }
                  });
    }

}
