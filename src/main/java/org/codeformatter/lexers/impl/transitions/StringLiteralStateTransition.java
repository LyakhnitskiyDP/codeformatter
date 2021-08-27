package org.codeformatter.lexers.impl.transitions;

import static org.codeformatter.lexers.impl.State.StateName.SIMPLE_TEXT;
import static org.codeformatter.lexers.impl.State.StateName.STRING_LITERAL;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.utils.PredicateUtil.any;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;

public class StringLiteralStateTransition extends StateTransition {


    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {

                ifToken(this::stringLiteralIsEnded)
                        .thenState(() -> State.of(SIMPLE_TEXT)),

                ifToken(any())
                        .thenState(() -> State.of(STRING_LITERAL))
        };
    }

    private boolean stringLiteralIsEnded(Token token) {

        return token.getLexeme().length() > 1 && token.getLexeme().endsWith(QUOTES);
    }
}
