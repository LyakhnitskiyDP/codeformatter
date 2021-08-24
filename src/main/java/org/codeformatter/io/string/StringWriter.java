package org.codeformatter.io.string;

import lombok.RequiredArgsConstructor;
import org.codeformatter.exceptions.CloseException;
import org.codeformatter.io.Writer;

@RequiredArgsConstructor
public class StringWriter implements Writer {

    private final StringBuilder stringBuilder;

    @Override
    public void writeChar(char ch) {

        stringBuilder.append(ch);
    }

    public String getResult() {

        return stringBuilder.toString();
    }

    @Override
    public void close() throws CloseException {
       //Implementation is left empty intentionally
    }
}
