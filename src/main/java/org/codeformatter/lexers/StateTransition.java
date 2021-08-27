package org.codeformatter.lexers;

import static java.util.Arrays.stream;

import java.util.function.Function;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.lexers.impl.transitions.ConditionalStateChange;
import org.codeformatter.tokens.Token;

public abstract class StateTransition implements Function<Token, State> {

    protected abstract ConditionalStateChange[] getConditionalStateChanges();

    @Override
    public State apply(Token token) {

        return stream(getConditionalStateChanges())
                .filter(conditionalStateChange -> conditionalStateChange.conditionIsMet(token))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No state is found for this token: " + token.toString()))
                .getState();
    }

}
