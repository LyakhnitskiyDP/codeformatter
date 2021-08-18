package org.codeformatter.io.file;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.io.Reader;

public class FileReader implements Reader, AutoCloseable {

    //TODO
    private final Scanner scanner;

    public FileReader(Path pathToFile) throws FileNotFoundException {

        this.scanner = new Scanner(pathToFile.toFile());

        //Make scanner read every character
        this.scanner.useDelimiter("");
    }

    @Override
    public void close() {

        scanner.close();
    }


    @Override
    public char readChar() {

        char charToReturn;

        try {
            charToReturn = scanner.next().charAt(0);
        } catch (NoSuchElementException elementException) {
            throw new ReaderException("No chars to read left");
        }

        return charToReturn;
    }

    @Override
    public boolean hasMoreChars() {

        return scanner.hasNext();
    }
}
