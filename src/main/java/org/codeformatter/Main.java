package org.codeformatter;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.exceptions.FormatterException;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.exceptions.WriterException;
import org.codeformatter.formatters.Formatter;
import org.codeformatter.formatters.impl.StateMachineFormatter;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.file.FileReader;
import org.codeformatter.io.file.FileWriter;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.impl.StateMachineLexer;

@Slf4j
public class Main {

    public static void main(String[] args) throws FormatterException {

        runFormatter(args);

    }

    private static void runFormatter(String[] args) throws FormatterException {
        Formatter formatter = new StateMachineFormatter();

        try (
                Reader fileReader = new FileReader(args[0]);
                Writer fileWriter = new FileWriter(args[1])
        ) {

            Lexer lexer = new StateMachineLexer(fileReader);

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
