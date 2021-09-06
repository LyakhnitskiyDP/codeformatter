package org.codeformatter.formatters.impl.commands;

import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterContext;
import org.codeformatter.tokens.Token;

public class WriteInitialClosingCurlyBracketCommand implements FormatterCommand {
    @Override
    public void execute(Token token, FormatterContext context) {
        context.decreaseIndentation();
        context.writeIndent();
        context.writeToken(token);
        context.writeNewLine();
    }
}
