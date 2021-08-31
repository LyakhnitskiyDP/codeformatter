package org.codeformatter.formatters;

import org.codeformatter.formatters.impl.FormatterState;
import org.codeformatter.tokens.Token;

public interface FormatterCommandRepository {

    FormatterCommand getCommand(FormatterState formatterState, Token token);

}
