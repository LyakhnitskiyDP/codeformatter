package org.codeformatter.io.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import org.codeformatter.exceptions.WriterException;
import org.codeformatter.io.Writer;


public class FileWriter implements Writer, AutoCloseable {

    private final java.io.FileWriter fileWriter;
    private final BufferedWriter bufferedWriter;

    public FileWriter(Path pathToFile) throws IOException {

        fileWriter = new java.io.FileWriter(pathToFile.toFile());
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public void close() throws Exception {

        bufferedWriter.close();
        fileWriter.close();
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
