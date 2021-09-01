package org.codeformatter.lexers.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.LexerStateTransitions;

import java.util.Map;

public class DefaultLexerStateTransitions implements LexerStateTransitions {

    private static final LexerState DEFAULT_LEXER_STATE = LexerState.of(LexerState.TERMINATED);

    private static final Map<Pair<LexerState, Character>, LexerState> stateChangesMap = Map.of(
            Pair.of(LexerState.of(LexerState.INITIAL), ' '), LexerState.of(LexerState.TERMINATED),
            Pair.of(LexerState.of(LexerState.INITIAL), ';'), LexerState.of(LexerState.TERMINATED),
            Pair.of(LexerState.of(LexerState.INITIAL), 'f'), LexerState.of("for_1"),
            Pair.of(LexerState.of("for_1"), 'o'), LexerState.of("for_2"),
            Pair.of(LexerState.of("for_2"), 'r'), LexerState.of("for"),
            Pair.of(LexerState.of("for"), ')'), LexerState.of(LexerState.TERMINATED)
    );

    @Override
    public LexerState nextState(LexerState lexerState, char ch) {

        LexerState lexerStateToReturn = stateChangesMap.get(Pair.of(lexerState, ch));


        if (lexerStateToReturn == null && lexerState.getState().equals("for")) {
            return LexerState.of("for");
        }

        if (lexerStateToReturn == null) {
            return DEFAULT_LEXER_STATE;
        }

        return lexerStateToReturn;
    }

}
