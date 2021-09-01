package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.LexerState.*;
import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.impl.FormatterState;
import org.codeformatter.lexers.LexerStateTransitions;

import java.util.Map;

public class DefaultLexerStateTransitions implements LexerStateTransitions {

    private static final Map<LexerState, Map<Character, LexerState>> transitionMaps;
    private static final Map<Character, LexerState> initialStateTransitions;
    private static final Map<Character, LexerState> forLoop1StateTransitions;
    private static final Map<Character, LexerState> forLoop2StateTransitions;
    private static final Map<Character, LexerState> forLoop3StateTransitions;
    private static final Map<Character, LexerState> forLoopStateTransitions;

    private static final Map<LexerState, LexerState> defaultStateTransitions;

    static {

        initialStateTransitions = Map.of(
            ' ', LexerState.of(TERMINATED),
            ';', LexerState.of(TERMINATED),
            'f', LexerState.of("for_1")
        );

        forLoop1StateTransitions = Map.of('o', LexerState.of(FOR_1));
        forLoop2StateTransitions = Map.of('r', LexerState.of(FOR_2));
        forLoop3StateTransitions = Map.of(' ', LexerState.of(FOR));
        forLoopStateTransitions = Map.of(')', LexerState.of(TERMINATED));

        transitionMaps = Map.of(
                LexerState.of(INITIAL), initialStateTransitions,
                LexerState.of(FOR_1), forLoop1StateTransitions,
                LexerState.of(FOR_2), forLoop2StateTransitions,
                LexerState.of(FOR_3), forLoop3StateTransitions,
                LexerState.of(FOR), forLoopStateTransitions
        );

        defaultStateTransitions = Map.of(
                LexerState.of(INITIAL), LexerState.of(TERMINATED),
                LexerState.of(FOR), LexerState.of(FOR),
                LexerState.of(FOR_1), LexerState.of(TERMINATED),
                LexerState.of(FOR_2), LexerState.of(TERMINATED),
                LexerState.of(FOR_3), LexerState.of(TERMINATED)
        );

    }

    private static final Map<Pair<LexerState, Character>, LexerState> stateChangesMap = Map.of(
            Pair.of(LexerState.of(INITIAL), ' '), LexerState.of(TERMINATED),
            Pair.of(LexerState.of(INITIAL), ';'), LexerState.of(TERMINATED),
            Pair.of(LexerState.of(INITIAL), 'f'), LexerState.of(FOR_1),
            Pair.of(LexerState.of(FOR_1), 'o'), LexerState.of(FOR_2),
            Pair.of(LexerState.of(FOR_2), 'r'), LexerState.of(FOR_3),
            Pair.of(LexerState.of(FOR_3), ' '), LexerState.of(FOR),
            Pair.of(LexerState.of(FOR), ')'), LexerState.of(LexerState.TERMINATED)
    );

    private Map<Character, LexerState> getTransitionsForState(LexerState state) {

        return transitionMaps.get(state);
    }

    private LexerState getDefaultStateTransitionFor(LexerState state) {

        return defaultStateTransitions.get(state);
    }

    @Override
    public LexerState nextState(LexerState lexerState, char ch) {

        return getTransitionsForState(lexerState)
                .getOrDefault(ch, getDefaultStateTransitionFor(lexerState));
    }

}
