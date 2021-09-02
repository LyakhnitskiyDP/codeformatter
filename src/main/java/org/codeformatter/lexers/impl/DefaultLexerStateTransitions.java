package org.codeformatter.lexers.impl;

import static org.codeformatter.utils.LoggingUtil.printChar;
import static org.codeformatter.lexers.impl.LexerState.FOR;
import static org.codeformatter.lexers.impl.LexerState.FOR_1;
import static org.codeformatter.lexers.impl.LexerState.FOR_2;
import static org.codeformatter.lexers.impl.LexerState.FOR_3;
import static org.codeformatter.lexers.impl.LexerState.INITIAL;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_END1;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_START1;
import static org.codeformatter.lexers.impl.LexerState.TERMINATED;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.LexerStateTransitions;

@Slf4j
public class DefaultLexerStateTransitions implements LexerStateTransitions {

    private final Map<Pair<String, Character>, String> transitions;

    public DefaultLexerStateTransitions() {

        transitions = new HashMap<>();

        transitions.putAll(Map.of(
                Pair.of(INITIAL, ' '), TERMINATED,
                Pair.of(INITIAL, ';'), TERMINATED,
                Pair.of(INITIAL, '/'), MULTILINE_COMMENT_START1,
                Pair.of(INITIAL, 'f'), FOR_1,
                Pair.of(INITIAL, null), TERMINATED
        ));

        transitions.putAll(Map.of(
                Pair.of(FOR_1, 'o'), FOR_2,
                Pair.of(FOR_1, null), TERMINATED,
                Pair.of(FOR_2, 'r'), FOR_3,
                Pair.of(FOR_2, null), TERMINATED,
                Pair.of(FOR_3, ' '), FOR,
                Pair.of(FOR_3, ';'), FOR,
                Pair.of(FOR_3, null), TERMINATED,
                Pair.of(FOR, ')'), TERMINATED,
                Pair.of(FOR, null), FOR
        ));

        transitions.putAll(Map.of(
                Pair.of(MULTILINE_COMMENT_START1, '*'), MULTILINE_COMMENT,
                Pair.of(MULTILINE_COMMENT_START1, null), INITIAL,
                Pair.of(MULTILINE_COMMENT, '*'), MULTILINE_COMMENT_END1,
                Pair.of(MULTILINE_COMMENT, null), MULTILINE_COMMENT,
                Pair.of(MULTILINE_COMMENT_END1, '/'), TERMINATED,
                Pair.of(MULTILINE_COMMENT_END1, null), MULTILINE_COMMENT
        ));
    }

    @Override
    public LexerState nextState(LexerState lexerState, char ch) {
        log.debug("Getting new lexer state, current lexer state: {}, char: {}", lexerState.getState(), printChar(ch));

        String lexerStateName = transitions.get(Pair.of(lexerState.getState(), ch));

        if (lexerStateName == null) {
            lexerStateName = transitions.get(Pair.of(lexerState.getState(), null));
        }

        log.debug("Returning new lexer state: {}", lexerStateName);
        return LexerState.of(lexerStateName);
    }

}
