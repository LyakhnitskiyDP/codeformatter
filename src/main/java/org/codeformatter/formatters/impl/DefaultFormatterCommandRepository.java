package org.codeformatter.formatters.impl;


import static org.codeformatter.tokens.LexicalConstants.*;
import static org.codeformatter.formatters.impl.FormatterState.*;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.tokens.Token;

import java.util.Map;

public class DefaultFormatterCommandRepository implements FormatterCommandRepository {

    private static final Map<FormatterState, Map<String, FormatterCommand>> commandMaps;
    private static final Map<String, FormatterCommand> initialStateCommands;
    private static final Map<String, FormatterCommand> writingLineStateCommands;
    private static final Map<String, FormatterCommand> writingStringLiteralStateCommands;

    private static final Map<FormatterState, FormatterCommand> defaultFormatterCommands;

    private static final FormatterCommand indentAndWriteCommand =
            (token, context) -> {
                context.writeIndent();
                context.writeToken(token);
            };

    private static final FormatterCommand writeTokenCommand =
            (token, context) -> {
                context.writeToken(token);
            };

    private static final FormatterCommand writeTokenAndNewLineCommand =
            (token, context) -> {
                context.writeToken(token);
                context.writeNewLine();
            };

    private static final FormatterCommand noOpCommand =
            (token, context) -> {

            };

    static {

        defaultFormatterCommands = Map.of(
            FormatterState.of(WRITING_STRING_LITERAL), writeTokenCommand
        );

        initialStateCommands = Map.of(
                CHAR, indentAndWriteCommand,
                FOR_LOOP, indentAndWriteCommand,
                MULTILINE_COMMENT, writeTokenAndNewLineCommand,
                WHITE_SPACE, noOpCommand,
                LINE_SEPARATOR, noOpCommand,
                SEMICOLON, writeTokenAndNewLineCommand,

                OPENING_CURLY_BRACKET, (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                    context.writeIndent();
                },
                CLOSING_CURLY_BRACKET, (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                    context.writeNewLine();
                }
            );

        writingStringLiteralStateCommands = Map.of();

        writingLineStateCommands = Map.of(
                CHAR, writeTokenCommand,

                QUOTES, writeTokenCommand,

                FOR_LOOP, writeTokenCommand,

                MULTILINE_COMMENT, writeTokenAndNewLineCommand,

                WHITE_SPACE, writeTokenCommand,

                LINE_SEPARATOR, noOpCommand,

                SEMICOLON, writeTokenAndNewLineCommand,

                OPENING_CURLY_BRACKET, (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                },

                CLOSING_CURLY_BRACKET, (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                }
        );

        commandMaps = Map.of(
                FormatterState.of(INITIAL), initialStateCommands,
                FormatterState.of(WRITING_LINE), writingLineStateCommands,
                FormatterState.of(WRITING_STRING_LITERAL), writingStringLiteralStateCommands
        );

    }

    private Map<String, FormatterCommand> getCommandMapForState(FormatterState state) {

        return commandMaps.get(state);
    }

    private FormatterCommand getDefaultCommandForState(FormatterState state) {

        return defaultFormatterCommands.get(state);
    }

    @Override
    public FormatterCommand getCommand(
            FormatterState formatterState,
            Token token) {


        return getCommandMapForState(formatterState)
                .getOrDefault(token.getName(), getDefaultCommandForState(formatterState));
    }

}
