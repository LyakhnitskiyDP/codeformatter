package org.codeformatter;

import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.exceptions.FormatterException;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.exceptions.WriterException;
import org.codeformatter.formatters.ChainedFormatter;
import org.codeformatter.io.file.FileReader;
import org.codeformatter.io.file.FileWriter;

@Slf4j
public class Main {

    public static void main(String[] args) throws FormatterException {

        ChainedFormatter chainedFormatter = new ChainedFormatter();

        try (
                FileReader fileReader = new FileReader(Path.of(args[0]));
                FileWriter fileWriter = new FileWriter(Path.of(args[1]))
        ) {

            chainedFormatter.format(fileReader, fileWriter);

        } catch (ReaderException readerException) {
            log.error("Unable to create file reader", readerException);
            throw new FormatterException("Unable to create file reader", readerException);
        } catch (WriterException writerException) {
            log.error("Unable to create file writer", writerException);
            throw new FormatterException("Unable to create file writer", writerException);
        }
    }
}
