package org.codeformatter.formatters;

import org.codeformatter.formatters.impl.FormatterState;
import org.codeformatter.tokens.Token;

public interface FormatterStateTransitions {

    FormatterState nextState(FormatterState state, Token token);

}
