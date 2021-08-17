package org.codeformatter.io.string;

import lombok.RequiredArgsConstructor;
import org.codeformatter.exceptions.ReaderException;
import org.codeformatter.io.Reader;

@RequiredArgsConstructor
public class StringReader implements Reader {

    private final String content;

    private int lastReadIndex = 0;

    @Override
    public char readChar() {

        char charToReturn;

        try {
            charToReturn = content.charAt(lastReadIndex++);
        } catch (IndexOutOfBoundsException indexException) {
            throw new ReaderException("Index of the char to read is out of bounds", indexException);
        }

        return charToReturn;
    }

    @Override
    public boolean hasMoreChars() {
        return lastReadIndex < content.length();
    }
}
