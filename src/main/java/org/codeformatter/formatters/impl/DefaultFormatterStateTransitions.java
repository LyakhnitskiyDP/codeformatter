package org.codeformatter.formatters.impl;

import static org.codeformatter.formatters.impl.FormatterState.INITIAL;
import static org.codeformatter.formatters.impl.FormatterState.MULTILINE_COMMENT_START_1;
import static org.codeformatter.formatters.impl.FormatterState.WRITING_LINE;
import static org.codeformatter.formatters.impl.FormatterState.WRITING_MULTILINE_COMMENT;
import static org.codeformatter.formatters.impl.FormatterState.WRITING_STRING_LITERAL;
import static org.codeformatter.tokens.LexicalConstants.CHAR;
import static org.codeformatter.tokens.LexicalConstants.CLOSING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.LINE_SEPARATOR;
import static org.codeformatter.tokens.LexicalConstants.MULTILINE_COMMENT;
import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.tokens.LexicalConstants.SEMICOLON;
import static org.codeformatter.tokens.LexicalConstants.SLASH;
import static org.codeformatter.tokens.LexicalConstants.WHITE_SPACE;

import java.util.HashMap;
import java.util.Map;

import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.FormatterStateTransitions;
import org.codeformatter.tokens.Token;

public class DefaultFormatterStateTransitions implements FormatterStateTransitions {

    private final Map<Pair<String, String>, String> transitions;

    public DefaultFormatterStateTransitions() {

        transitions = new HashMap<>();

        transitions.putAll(Map.of(
                Pair.of(INITIAL, null), WRITING_LINE,
                Pair.of(INITIAL, CHAR), WRITING_LINE,
                Pair.of(INITIAL, WHITE_SPACE), INITIAL,
                Pair.of(INITIAL, LINE_SEPARATOR), INITIAL,
                Pair.of(INITIAL, MULTILINE_COMMENT), INITIAL,
                Pair.of(INITIAL, SEMICOLON), INITIAL,
                Pair.of(INITIAL, OPENING_CURLY_BRACKET), INITIAL,
                Pair.of(INITIAL, CLOSING_CURLY_BRACKET), INITIAL,
                Pair.of(INITIAL, QUOTES), WRITING_STRING_LITERAL,
                Pair.of(INITIAL,SLASH), MULTILINE_COMMENT_START_1
        ));

        transitions.putAll(Map.of(
                Pair.of(WRITING_STRING_LITERAL, null), WRITING_STRING_LITERAL
        ));

        transitions.putAll(Map.of(
                Pair.of(WRITING_LINE, null), WRITING_LINE,
                Pair.of(WRITING_LINE, SEMICOLON), INITIAL,
                Pair.of(WRITING_LINE, OPENING_CURLY_BRACKET), INITIAL,
                Pair.of(WRITING_LINE, CLOSING_CURLY_BRACKET), INITIAL,
                Pair.of(WRITING_LINE, QUOTES), WRITING_STRING_LITERAL,
                Pair.of(WRITING_LINE, SLASH), MULTILINE_COMMENT_START_1
        ));

        transitions.putAll(Map.of(
                Pair.of(WRITING_MULTILINE_COMMENT, null), WRITING_MULTILINE_COMMENT,
                Pair.of(WRITING_MULTILINE_COMMENT, QUOTES), WRITING_LINE
        ));
    }

    @Override
    public FormatterState nextState(FormatterState state, Token token) {

        String formatterStateName = transitions.get(
                Pair.of(state.getState(), token.getName())
        );

        if (formatterStateName == null) {
            formatterStateName = transitions.get(Pair.of(state.getState(), null));
        }

        return FormatterState.of(formatterStateName);
    }
}
