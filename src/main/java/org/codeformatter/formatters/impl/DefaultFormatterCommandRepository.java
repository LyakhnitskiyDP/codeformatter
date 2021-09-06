package org.codeformatter.formatters.impl;


import static org.codeformatter.formatters.impl.FormatterState.IGNORING_WHITESPACES;
import static org.codeformatter.formatters.impl.FormatterState.INITIAL;
import static org.codeformatter.formatters.impl.FormatterState.WRITING_LINE;
import static org.codeformatter.formatters.impl.FormatterState.WRITING_STRING_LITERAL;
import static org.codeformatter.tokens.LexicalConstants.CARRIAGE_RETURN;
import static org.codeformatter.tokens.LexicalConstants.CHAR;
import static org.codeformatter.tokens.LexicalConstants.CLOSING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.FOR_LOOP;
import static org.codeformatter.tokens.LexicalConstants.LINE_SEPARATOR;
import static org.codeformatter.tokens.LexicalConstants.MULTILINE_COMMENT;
import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.tokens.LexicalConstants.SEMICOLON;
import static org.codeformatter.tokens.LexicalConstants.WHITE_SPACE;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.tokens.Token;

@Slf4j
@Deprecated
public class DefaultFormatterCommandRepository implements FormatterCommandRepository {

    //First String Type parameter - current state name
    //Second String Type parameter - token name
    //Third parameter - command to execute
    private Map<Pair<String, String>, FormatterCommand> commands;

    FormatterCommand indentAndWriteCommand =
            (token, context) -> {
                context.writeIndent();
                context.writeToken(token);
            };

    FormatterCommand writeTokenCommand =
            (token, context) -> {
                context.writeToken(token);
            };

    FormatterCommand writeTokenAndNewLineCommand =
            (token, context) -> {
                context.writeToken(token);
                context.writeNewLine();
            };

    FormatterCommand noOpCommand =
            (token, context) -> {

            };

    public DefaultFormatterCommandRepository() {
        commands = new HashMap<>();

        commands.putAll(Map.of(
                Pair.of(INITIAL, CHAR), indentAndWriteCommand,
                Pair.of(INITIAL, FOR_LOOP), indentAndWriteCommand,
                Pair.of(INITIAL, MULTILINE_COMMENT), writeTokenAndNewLineCommand,
                Pair.of(INITIAL, WHITE_SPACE), noOpCommand,
                Pair.of(INITIAL, LINE_SEPARATOR), noOpCommand,
                Pair.of(INITIAL, CARRIAGE_RETURN), noOpCommand,
                Pair.of(INITIAL, SEMICOLON), writeTokenAndNewLineCommand,
                Pair.of(INITIAL, OPENING_CURLY_BRACKET), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                    context.writeIndent();
                },
                Pair.of(INITIAL, CLOSING_CURLY_BRACKET), (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                    context.writeNewLine();
                }
        ));

        commands.putAll(Map.of(
                Pair.of(WRITING_LINE, CHAR), writeTokenCommand,

                Pair.of(WRITING_LINE, QUOTES), writeTokenCommand,

                Pair.of(WRITING_LINE, FOR_LOOP), writeTokenCommand,

                Pair.of(WRITING_LINE, MULTILINE_COMMENT), writeTokenAndNewLineCommand,

                Pair.of(WRITING_LINE, WHITE_SPACE), writeTokenCommand,

                Pair.of(WRITING_LINE, LINE_SEPARATOR), noOpCommand,

                Pair.of(WRITING_LINE, SEMICOLON), writeTokenAndNewLineCommand,

                Pair.of(WRITING_LINE, OPENING_CURLY_BRACKET), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                },

                Pair.of(WRITING_LINE, CLOSING_CURLY_BRACKET), (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                }
        ));

        commands.putAll(Map.of(
                Pair.of(WRITING_STRING_LITERAL, null), writeTokenCommand
        ));

        commands.putAll(Map.of(
                Pair.of(IGNORING_WHITESPACES, WHITE_SPACE), noOpCommand,

                Pair.of(IGNORING_WHITESPACES, CHAR), writeTokenCommand,

                Pair.of(IGNORING_WHITESPACES, QUOTES), writeTokenCommand,

                Pair.of(IGNORING_WHITESPACES, FOR_LOOP), writeTokenCommand,

                Pair.of(IGNORING_WHITESPACES, MULTILINE_COMMENT), writeTokenAndNewLineCommand,

                Pair.of(IGNORING_WHITESPACES, LINE_SEPARATOR), noOpCommand,

                Pair.of(IGNORING_WHITESPACES, SEMICOLON), writeTokenAndNewLineCommand,

                Pair.of(IGNORING_WHITESPACES, OPENING_CURLY_BRACKET), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                },

                Pair.of(IGNORING_WHITESPACES, CLOSING_CURLY_BRACKET), (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                }
        ));

    }

    @Override
    public FormatterCommand getCommand(
            FormatterState formatterState,
            Token token) {
        log.debug("Getting new formatter command for state: {} and token: {}",
                  formatterState.getState(), token.getName());

        FormatterCommand commandToReturn = commands.get(
                Pair.of(formatterState.getState(), token.getName())
        );

        if (commandToReturn == null) {
            log.debug("No special formatter command found, returning default one");
            commandToReturn = commands.get(Pair.of(formatterState.getState(), null));
        }

        return commandToReturn;
    }
}
