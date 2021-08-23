package org.codeformatter.io.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.exceptions.CloseException;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.io.Closable;
import org.codeformatter.io.Reader;

@Slf4j
public class FileReader implements Reader, Closable {

    private final InputStreamReader inputStreamReader;

    private int currentIntChar;

    public FileReader(Path pathToFile) throws ReaderException {

        try {
            FileInputStream fileInputStream = new FileInputStream(pathToFile.toFile());
            this.inputStreamReader = new InputStreamReader(fileInputStream);

            readNextChar();

        } catch (FileNotFoundException fileNotFoundException) {
            log.error("File with path: {} not found", pathToFile.toString());
            throw new ReaderException("File to format not found", fileNotFoundException);
        } catch (IOException ioException) {
            log.error("IOException while reading file: {}", pathToFile.toString());
            throw new ReaderException("Unable to read from file", ioException);
        }

    }

    @Override
    public void close() throws CloseException {

        try {
            inputStreamReader.close();
        } catch (IOException ioException) {
            throw new CloseException("Exception while file closing", ioException);
        }

    }

    @Override
    public char readChar() {

        if (isFileEnd(currentIntChar)) {
            throw new ReaderException("No characters to read are left");
        }

        int previousIntChar = currentIntChar;

        try {
            readNextChar();
        } catch (IOException ioException) {
            log.error("IOException while reading file");
            throw new ReaderException("Unable to read from file", ioException);
        }

        return (char) previousIntChar;
    }

    private void readNextChar() throws IOException {
        currentIntChar = inputStreamReader.read();
    }

    @Override
    public boolean hasMoreChars() {

        return ! isFileEnd(currentIntChar);
    }

    private boolean isFileEnd(int intCh) {
        return intCh == -1;
    }

    @Override
    public boolean hasNext() {
        return hasMoreChars();
    }

    @Override
    public Character next() {
        return readChar();
    }
}
