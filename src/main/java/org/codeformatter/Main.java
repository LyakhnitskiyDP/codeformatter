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

import java.util.Arrays;

@Slf4j
public class Main {
    private static final String DEFAULT_PATH_TO_INPUT_FILE = "src/main/resources/testSourceCode.java";
    private static final String DEFAULT_PATH_TO_OUTPUT_FILE = "src/main/resources/output.java";

    public static void main(String[] args) throws FormatterException {

        runFormatter(args);

    }

    private static void runFormatter(String[] args) throws FormatterException {
        Formatter formatter = new StateMachineFormatter();

        args = Arrays.copyOf(args, 2);
        String pathToInputFile = args[0] != null ? args[0] : DEFAULT_PATH_TO_INPUT_FILE;
        String pathToOutputFile = args[1] != null ? args[1] : DEFAULT_PATH_TO_OUTPUT_FILE;

        try (
                Reader fileReader = new FileReader(pathToInputFile);
                Writer fileWriter = new FileWriter(pathToOutputFile)
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
