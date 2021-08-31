package org.codeformatter.lexers;

import org.codeformatter.lexers.impl.State;

public interface StateTransitions {

    State nextState(State state, char ch);

}
