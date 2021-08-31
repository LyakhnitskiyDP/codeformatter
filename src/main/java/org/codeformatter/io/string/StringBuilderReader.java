package org.codeformatter.io.string;

import lombok.RequiredArgsConstructor;
import org.codeformatter.exceptions.CloseException;
import org.codeformatter.io.Reader;

@RequiredArgsConstructor
public class StringBuilderReader implements Reader {

    private final StringBuilder content;

    private int lastReadIndex = 0;

    @Override
    public char readChar() {
        return content.charAt(lastReadIndex++);
    }

    @Override
    public boolean hasMoreChars() {
        return lastReadIndex < content.length();
    }

    @Override
    public void close() throws CloseException {

    }

}
