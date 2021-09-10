package org.codeformatter.formatters.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.formatters.FormatterContext;
import org.codeformatter.io.Writer;
import org.codeformatter.tokens.Token;

@RequiredArgsConstructor
@Slf4j
class StateMachineFormatterContext implements FormatterContext {
    private final int numberOfSpacesPerTab = 4;

    private int currentBlockLevel = 0;

    private final Writer writer;

    @Override
    public void writeToken(Token token) {

        log.trace("Writing token: {}", token);
        for (char ch : token.getLexeme().toCharArray()) {
            writer.writeChar(ch);
        }
    }

    @Override
    public void writeNewLine() {

        log.trace("Writing new line");
        for (char ch : System.lineSeparator().toCharArray()) {
            System.out.println("WRITING: " + ((int) ch));
            writer.writeChar(ch);
        }
    }

    @Override
    public void writeIndent() {

        log.trace("Writing indentation");
        for (int i = 0; i < currentBlockLevel * numberOfSpacesPerTab; i++) {
            writer.writeChar(' ');
        }
    }

    @Override
    public void increaseIndentation() {

        log.trace("Increasing block level");
        currentBlockLevel++;
    }

    @Override
    public void decreaseIndentation() {

        log.trace("Decreasing block level");
        currentBlockLevel--;
    }
}
