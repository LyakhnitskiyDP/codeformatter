package org.codeformatter.formatters.impl;

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
                "Char", FormatterState.of(WRITING_LINE),
                "Whitespace", FormatterState.of(INITIAL),
                "Line separator", FormatterState.of(INITIAL),
                "Semicolon", FormatterState.of(INITIAL),
                "Opening Curly Bracket", FormatterState.of(INITIAL),
                "Closing Curly Bracket", FormatterState.of(INITIAL),
                "Quotes", FormatterState.of(WRITING_STRING_LITERAL),
                "Slash", FormatterState.of(MULTILINE_COMMENT_START_1)
        );

        writingLineStateTransitions = Map.of(
                "Semicolon", FormatterState.of(INITIAL),
                "Opening Curly Bracket", FormatterState.of(INITIAL),
                "Closing Curly Bracket", FormatterState.of(INITIAL),
                "Quotes", FormatterState.of(WRITING_STRING_LITERAL),
                "Slash", FormatterState.of(MULTILINE_COMMENT_START_1)
        );

        writingStringLiteralStateTransitions = Map.of(
                "Quotes", FormatterState.of(WRITING_LINE)
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