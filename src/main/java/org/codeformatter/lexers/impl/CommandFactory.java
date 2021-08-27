package org.codeformatter.lexers.impl;

import lombok.RequiredArgsConstructor;
import org.codeformatter.lexers.Command;
import org.codeformatter.lexers.impl.transitions.StateTransitions;
import org.codeformatter.tokens.Token;

@RequiredArgsConstructor
public class CommandFactory {

    private final State state;

    public Command getCommend(Token currentToken) {

        StateTransitions transitions = new StateTransitions();

        return () -> {

            State updatedState = transitions.nextState(state, currentToken);

            state.setName(updatedState.getName());
        };
    }

}
