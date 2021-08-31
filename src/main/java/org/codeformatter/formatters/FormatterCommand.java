package org.codeformatter.formatters;

import org.codeformatter.tokens.Token;

public interface FormatterCommand {

    void execute(Token token, FormatterContext context);
}
