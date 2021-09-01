package org.codeformatter.formatters.impl;

import static org.codeformatter.tokens.LexicalConstants.*;
import static org.codeformatter.formatters.impl.FormatterState.*;

import org.codeformatter.formatters.FormatterStateTransitions;
import org.codeformatter.tokens.Token;

import java.util.Map;

public class DefaultFormatterStateTransitions implements FormatterStateTransitions {

    private static final Map<FormatterState, Map<String, FormatterState>> transitionMaps;
    private static final Map<FormatterState, FormatterState> defaultStateTransitions;

    private static final Map<String, FormatterState> initialStateTransitions;
    private static final Map<String, FormatterState> writingLineStateTransitions;
    private static final Map<String, FormatterState> writingStringLiteralStateTransitions;

    static {
        defaultStateTransitions = Map.of(
                FormatterState.of(WRITING_STRING_LITERAL), FormatterState.of(WRITING_STRING_LITERAL),
                FormatterState.of(INITIAL), FormatterState.of(WRITING_LINE),
                FormatterState.of(WRITING_LINE), FormatterState.of(WRITING_LINE),
                FormatterState.of(WRITING_MULTILINE_COMMENT), FormatterState.of(WRITING_MULTILINE_COMMENT)
        );

        initialStateTransitions = Map.of(
                CHAR, FormatterState.of(WRITING_LINE),
                WHITE_SPACE, FormatterState.of(INITIAL),
                LINE_SEPARATOR, FormatterState.of(INITIAL),
                SEMICOLON, FormatterState.of(INITIAL),
                OPENING_CURLY_BRACKET, FormatterState.of(INITIAL),
                CLOSING_CURLY_BRACKET, FormatterState.of(INITIAL),
                QUOTES, FormatterState.of(WRITING_STRING_LITERAL),
                SLASH, FormatterState.of(MULTILINE_COMMENT_START_1)
        );

        writingLineStateTransitions = Map.of(
                SEMICOLON, FormatterState.of(INITIAL),
                OPENING_CURLY_BRACKET, FormatterState.of(INITIAL),
                CLOSING_CURLY_BRACKET, FormatterState.of(INITIAL),
                QUOTES, FormatterState.of(WRITING_STRING_LITERAL),
                SLASH, FormatterState.of(MULTILINE_COMMENT_START_1)
        );

        writingStringLiteralStateTransitions = Map.of(
                QUOTES, FormatterState.of(WRITING_LINE)
        );

        transitionMaps = Map.of(
                FormatterState.of(INITIAL), initialStateTransitions,
                FormatterState.of(WRITING_LINE), writingLineStateTransitions,
                FormatterState.of(WRITING_STRING_LITERAL), writingStringLiteralStateTransitions
        );

    }

    private Map<String, FormatterState> getTransitionsForState(FormatterState state) {

        return transitionMaps.get(state);
    }

    private FormatterState getDefaultStateTransitionFor(FormatterState state) {

        return defaultStateTransitions.get(state);
    }

    @Override
    public FormatterState nextState(FormatterState state, Token token) {

        return getTransitionsForState(state)
                .getOrDefault(token.getName(), getDefaultStateTransitionFor(state));
    }

}