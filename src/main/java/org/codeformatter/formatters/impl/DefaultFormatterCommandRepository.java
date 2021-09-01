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

    static {

        defaultFormatterCommands = Map.of(
            FormatterState.of(WRITING_STRING_LITERAL), (commandToken, context) -> { context.writeToken(commandToken); }
        );

        initialStateCommands = Map.of(
                CHAR, (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                FOR_LOOP, (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                WHITE_SPACE, (token, context) -> {
                    //Intentionally left empty
                },
                LINE_SEPARATOR, (token, context) -> {
                    //Intentionally left empty
                },
                SEMICOLON, (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                },
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
                CHAR, (token, context) -> { context.writeToken(token); },

                QUOTES, (token, context) -> { context.writeToken(token); },

                FOR_LOOP, (token, context) -> { context.writeToken(token); },

                WHITE_SPACE, (token, context) -> { context.writeToken(token); },

                LINE_SEPARATOR, (token, context) -> {
                    //Intentionally left empty
                },

                SEMICOLON, (token, context) -> {
                     context.writeToken(token);
                     context.writeNewLine();
                },

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
