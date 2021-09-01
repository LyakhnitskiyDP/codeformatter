package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.LexerState.FOR;
import static org.codeformatter.lexers.impl.LexerState.FOR_1;
import static org.codeformatter.lexers.impl.LexerState.FOR_2;
import static org.codeformatter.lexers.impl.LexerState.FOR_3;
import static org.codeformatter.lexers.impl.LexerState.INITIAL;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_END1;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_START1;
import static org.codeformatter.lexers.impl.LexerState.TERMINATED;

import java.util.Map;
import org.codeformatter.lexers.LexerStateTransitions;

public class DefaultLexerStateTransitions implements LexerStateTransitions {

    private static final Map<LexerState, Map<Character, LexerState>> transitionMaps;
    private static final Map<Character, LexerState> initialStateTransitions;

    private static final Map<Character, LexerState> forLoop1StateTransitions;
    private static final Map<Character, LexerState> forLoop2StateTransitions;
    private static final Map<Character, LexerState> forLoop3StateTransitions;
    private static final Map<Character, LexerState> forLoopStateTransitions;

    private static final Map<Character, LexerState> multilineCommentStart1Transitions;
    private static final Map<Character, LexerState> multilineCommentTransitions;
    private static final Map<Character, LexerState> multilineCommentEnd1Transitions;

    private static final Map<LexerState, LexerState> defaultStateTransitions;

    static {

        initialStateTransitions = Map.of(
            ' ', LexerState.of(TERMINATED),
            ';', LexerState.of(TERMINATED),
            '/', LexerState.of(MULTILINE_COMMENT_START1),
            'f', LexerState.of(FOR_1)
        );

        forLoop1StateTransitions = Map.of('o', LexerState.of(FOR_2));
        forLoop2StateTransitions = Map.of('r', LexerState.of(FOR_3));
        forLoop3StateTransitions = Map.of(' ', LexerState.of(FOR));
        forLoopStateTransitions = Map.of(')', LexerState.of(TERMINATED));

        multilineCommentStart1Transitions = Map.of('*', LexerState.of(MULTILINE_COMMENT));
        multilineCommentTransitions = Map.of('*', LexerState.of(MULTILINE_COMMENT_END1));
        multilineCommentEnd1Transitions = Map.of('/', LexerState.of(TERMINATED));

        transitionMaps = Map.of(
                LexerState.of(INITIAL), initialStateTransitions,
                LexerState.of(FOR_1), forLoop1StateTransitions,
                LexerState.of(FOR_2), forLoop2StateTransitions,
                LexerState.of(FOR_3), forLoop3StateTransitions,
                LexerState.of(FOR), forLoopStateTransitions,
                LexerState.of(MULTILINE_COMMENT), multilineCommentTransitions,
                LexerState.of(MULTILINE_COMMENT_START1), multilineCommentStart1Transitions,
                LexerState.of(MULTILINE_COMMENT_END1), multilineCommentEnd1Transitions
        );

        defaultStateTransitions = Map.of(
                LexerState.of(INITIAL), LexerState.of(TERMINATED),
                LexerState.of(FOR), LexerState.of(FOR),
                LexerState.of(FOR_1), LexerState.of(TERMINATED),
                LexerState.of(FOR_2), LexerState.of(TERMINATED),
                LexerState.of(FOR_3), LexerState.of(TERMINATED),
                LexerState.of(MULTILINE_COMMENT), LexerState.of(MULTILINE_COMMENT),
                LexerState.of(MULTILINE_COMMENT_START1), LexerState.of(INITIAL),
                LexerState.of(MULTILINE_COMMENT_END1), LexerState.of(MULTILINE_COMMENT)
        );

    }

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
