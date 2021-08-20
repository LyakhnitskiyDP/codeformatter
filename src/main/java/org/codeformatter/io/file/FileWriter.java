package org.codeformatter.io.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import org.codeformatter.exceptions.CloseException;
import org.codeformatter.exceptions.WriterException;
import org.codeformatter.io.Closable;
import org.codeformatter.io.Writer;


public class FileWriter implements Writer, Closable {

    private final java.io.FileWriter fileWriter;
    private final BufferedWriter bufferedWriter;

    public FileWriter(Path pathToFile) throws WriterException {

        try {
            fileWriter = new java.io.FileWriter(pathToFile.toFile());
        } catch (IOException ioException) {
            throw new WriterException("Unable to open file writer", ioException);
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public void close() throws CloseException {

        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception exception) {
            throw new CloseException("Exception while closing file writer", exception);
        }
    }

    @Override
    public void writeChar(char ch) {

        try {
            bufferedWriter.write(String.valueOf(ch));
        } catch (IOException ioException) {
            throw new WriterException("Unable to write to file", ioException);
        }

    }
}
