package org.codeformatter.formatters.impl;

import static org.codeformatter.formatters.impl.FormatterState.*;

import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.FormatterStateTransitions;
import org.codeformatter.tokens.Token;

import java.util.HashMap;
import java.util.Map;


public class DefaultFormatterStateTransitions implements FormatterStateTransitions {

    private static final Map<Pair<FormatterState, String>, FormatterState> transitions = new HashMap<>();

    private static final Map<String, FormatterState> initialStateTransitions;
    private static final Map<String, FormatterState> writingLineStateTransitions;
    private static final Map<String, FormatterState> writingStringLiteralStateTransitions;

    private static final FormatterState defaultStringLiteralState;

    private static final FormatterState defaultMultilineCommentState;
    private static final FormatterState defaultMultilineCommentStartState;
    private static final FormatterState defaultMultilineCommentEndState;

    static {
        defaultStringLiteralState = FormatterState.of(FormatterState.WRITING_STRING_LITERAL);

        defaultMultilineCommentState = FormatterState.of(FormatterState.WRITING_MULTILINE_COMMENT);
        defaultMultilineCommentStartState = FormatterState.of(FormatterState.WRITING_LINE);
        defaultMultilineCommentEndState = FormatterState.of(FormatterState.INITIAL);

        transitions.putAll(Map.of(
            Pair.of(FormatterState.of(INITIAL), "Char"), FormatterState.of(WRITING_LINE),
            Pair.of(FormatterState.of(INITIAL), "Whitespace"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(INITIAL), "Semicolon"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(INITIAL), "Opening Curly Bracket"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(INITIAL), "Closing Curly Bracket"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(INITIAL), "Quotes"), FormatterState.of(WRITING_STRING_LITERAL),
            Pair.of(FormatterState.of(INITIAL), "Slash"), FormatterState.of(MULTILINE_COMMENT_START_1)
        ));

        transitions.putAll(Map.of(
            Pair.of(FormatterState.of(WRITING_LINE), "Semicolon"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(WRITING_LINE), "Opening Curly Bracket"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(WRITING_LINE), "Closing Curly Bracket"), FormatterState.of(INITIAL),
            Pair.of(FormatterState.of(WRITING_LINE), "Quotes"), FormatterState.of(WRITING_STRING_LITERAL),
            Pair.of(FormatterState.of(WRITING_LINE), "Slash"), FormatterState.of(MULTILINE_COMMENT_START_1)
        ));

        transitions.putAll(Map.of(
                Pair.of(FormatterState.of(MULTILINE_COMMENT_START_1), "Star"), FormatterState.of(WRITING_MULTILINE_COMMENT),
                Pair.of(FormatterState.of(WRITING_MULTILINE_COMMENT), "Star"), FormatterState.of(MULTILINE_COMMENT_END_1),
                Pair.of(FormatterState.of(MULTILINE_COMMENT_END_1), "Slash"), FormatterState.of(MULTILINE_COMMENT_END_2),
                Pair.of(FormatterState.of(MULTILINE_COMMENT_END_2), "Whitespace"), FormatterState.of(INITIAL)
        ));

        transitions.putAll(Map.of(
                Pair.of(FormatterState.of(WRITING_STRING_LITERAL), "Quotes"), FormatterState.of(WRITING_LINE)
        ));

    }

    @Override
    public FormatterState nextState(FormatterState state, Token token) {

        FormatterState stateToReturn = transitions.get(Pair.of(state, token.getName()));

        // get transitions for state( state ).getOrDefault( token, defaultStringLiteralState );

        if (stateToReturn == null && stateIsStringLiteral(state)) {

            return defaultStringLiteralState;
        }

        if (stateToReturn == null && stateIsMultilineComment(state)) {

            return defaultMultilineCommentState;
        }

        if (stateToReturn == null && stateIsMultilineCommentEnd(state)) {

            return defaultMultilineCommentState;
        }

        if (stateToReturn == null && stateIsMultilineCommentEnd2(state)) {

            return defaultMultilineCommentEndState;
        }

        if (stateToReturn == null && stateIsMultilineCommentStart(state)) {

            return defaultMultilineCommentStartState;
        }

        if (stateToReturn == null) {
            return FormatterState.of(FormatterState.WRITING_LINE);
        }

        return stateToReturn;
    }

    private boolean stateIsStringLiteral(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.WRITING_STRING_LITERAL);
    }

    private boolean stateIsMultilineComment(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.WRITING_MULTILINE_COMMENT);
    }

    private boolean stateIsMultilineCommentStart(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.MULTILINE_COMMENT_START_1);
    }


    private boolean stateIsMultilineCommentEnd(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.MULTILINE_COMMENT_END_1);
    }

    private boolean stateIsMultilineCommentEnd2(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.MULTILINE_COMMENT_END_2);
    }
}
