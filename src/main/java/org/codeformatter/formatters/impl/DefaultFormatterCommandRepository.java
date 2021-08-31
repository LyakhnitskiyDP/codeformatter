package org.codeformatter.formatters.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.tokens.Token;

import java.util.HashMap;
import java.util.Map;

public class DefaultFormatterCommandRepository implements FormatterCommandRepository {

    private static final Map<Pair<FormatterState, String>, FormatterCommand> commands = new HashMap<>();

    private static final Map<FormatterState, Map<String, FormatterCommand>> commandMaps;
    private static final Map<String, FormatterCommand> initialStateCommands;
    private static final Map<String, FormatterCommand> writingLineStateCommands;
    private static final Map<String, FormatterCommand> writingStringLiteralStateCommands;

    private static final Map<FormatterState, FormatterCommand> defaultFormatterCommands;

    static {

        defaultFormatterCommands = Map.of(
            FormatterState.of(FormatterState.WRITING_STRING_LITERAL), (commandToken, context) -> { context.writeToken(commandToken); }
        );

        initialStateCommands = Map.of(
                "Char", (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                 "for", (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                "Whitespace", (token, context) -> {

                },
                "Semicolon", (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                },
                "Opening Curly Bracket", (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                    context.writeIndent();
                },
                "Closing Curly Bracket", (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                    context.writeNewLine();
                }
            );

        writingStringLiteralStateCommands = Map.of();

        writingLineStateCommands = Map.of(
                 "Char", (token, context) -> { context.writeToken(token); },

                "Quotes", (token, context) -> { context.writeToken(token); },

                "for", (token, context) -> { context.writeToken(token); },

                "Whitespace", (token, context) -> { context.writeToken(token); },

                "Semicolon", (token, context) -> {
                     context.writeToken(token);
                     context.writeNewLine();
                },

                "Opening Curly Bracket", (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                },

                "Closing Curly Bracket", (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                }
        );

        commandMaps = Map.of(
                FormatterState.of(FormatterState.INITIAL), initialStateCommands,
                FormatterState.of(FormatterState.WRITING_LINE), writingLineStateCommands,
                FormatterState.of(FormatterState.WRITING_STRING_LITERAL), writingStringLiteralStateCommands
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
