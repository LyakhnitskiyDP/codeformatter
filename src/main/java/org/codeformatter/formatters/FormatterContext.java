package org.codeformatter.formatters;

import org.codeformatter.tokens.Token;

public interface FormatterContext {

    void writeToken(Token token);

    void writeNewLine();

    void writeIndent();

    void increaseIndentation();

    void decreaseIndentation();

}
