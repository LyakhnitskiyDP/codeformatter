package org.codeformatter.io.string;

import lombok.RequiredArgsConstructor;
import org.codeformatter.io.Writer;

@RequiredArgsConstructor
public class StringWriter implements Writer {

    private final StringBuilder stringBuilder;

    @Override
    public void writeChar(char ch) {

        stringBuilder.append(ch);
    }
}
