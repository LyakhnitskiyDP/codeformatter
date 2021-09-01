package org.codeformatter.lexers;

import org.codeformatter.lexers.impl.LexerState;

public interface LexerStateTransitions {

    LexerState nextState(LexerState lexerState, char ch);

}
