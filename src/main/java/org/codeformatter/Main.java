package org.codeformatter;

import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.exceptions.FormatterException;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.exceptions.WriterException;
import org.codeformatter.formatters.impl.DefaultFormatter;
import org.codeformatter.io.file.FileReader;
import org.codeformatter.io.file.FileWriter;
import org.codeformatter.lexers.ChainedLexer;

@Slf4j
public class Main {

    public static void main(String[] args) throws FormatterException {

        runFormatter(args);

    }

    private static void runFormatter(String[] args) throws FormatterException {
        DefaultFormatter formatter = new DefaultFormatter();

        try (
                FileReader fileReader = new FileReader(Path.of(args[0]));
                FileWriter fileWriter = new FileWriter(Path.of(args[1]))
        ) {

            ChainedLexer lexer = new ChainedLexer(fileReader);

            formatter.format(lexer, fileWriter);

        } catch (ReaderException readerException) {
            log.error("Unable to create file reader", readerException);
            throw new FormatterException("Unable to create file reader", readerException);
        } catch (WriterException writerException) {
            log.error("Unable to create file writer", writerException);
            throw new FormatterException("Unable to create file writer", writerException);
        }
    }

}
