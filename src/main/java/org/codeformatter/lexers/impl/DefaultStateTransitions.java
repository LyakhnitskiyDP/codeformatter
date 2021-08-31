package org.codeformatter.lexers.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.StateTransitions;

import java.util.Map;

public class DefaultStateTransitions implements StateTransitions {

    private static final State defaultState = State.of(State.TERMINATED);

    private static final Map<Pair<State, Character>, State> stateChangesMap = Map.of(
            Pair.of(State.of(State.INITIAL), ' '), State.of(State.TERMINATED),
            Pair.of(State.of(State.INITIAL), ';'), State.of(State.TERMINATED),
            Pair.of(State.of(State.INITIAL), 'f'), State.of("for_1"),
            Pair.of(State.of("for_1"), 'o'), State.of("for_2"),
            Pair.of(State.of("for_2"), 'r'), State.of("for"),
            Pair.of(State.of("for"), ')'), State.of(State.TERMINATED)
    );

    @Override
    public State nextState(State state, char ch) {

        State stateToReturn = stateChangesMap.get(Pair.of(state, ch));

        if (stateToReturn == null && state.getState().equals("for")) {
            return State.of("for");
        }

        if (stateToReturn == null) {
            return defaultState;
        }

        return stateToReturn;
    }

}
