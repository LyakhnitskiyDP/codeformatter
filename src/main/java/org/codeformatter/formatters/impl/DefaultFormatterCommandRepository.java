package org.codeformatter.formatters.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.tokens.Token;

import java.util.HashMap;
import java.util.Map;

public class DefaultFormatterCommandRepository implements FormatterCommandRepository {

    private static final Map<Pair<FormatterState, String>, FormatterCommand> commands = new HashMap<>();

    private static final FormatterCommand defaultStringLiteralCommand;
    private static final FormatterCommand defaultMultilineCommentEndCommand;

    static {

        defaultStringLiteralCommand = (commandToken, context) -> { context.writeToken(commandToken); };
        defaultMultilineCommentEndCommand = (commandToken, context) -> { context.writeNewLine(); context.writeToken(commandToken);  };


        //Commands for INITIAL state
        commands.putAll(Map.of(
                Pair.of(FormatterState.of(FormatterState.INITIAL), "Char"), (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                Pair.of(FormatterState.of(FormatterState.INITIAL), "for"), (token, context) -> {
                    context.writeIndent();
                    context.writeToken(token);
                },
                Pair.of(FormatterState.of(FormatterState.INITIAL), "Whitespace"), (token, context) -> { },
                Pair.of(FormatterState.of(FormatterState.MULTILINE_COMMENT_END_2), "Whitespace"), (token, context) -> { },
                Pair.of(FormatterState.of(FormatterState.INITIAL), "Semicolon"), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                },
                Pair.of(FormatterState.of(FormatterState.INITIAL), "Opening Curly Bracket"), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                    context.writeIndent();
                },
                Pair.of(FormatterState.of(FormatterState.INITIAL), "Closing Curly Bracket"), (token, context) -> {
                    context.decreaseIndentation();
                    context.writeIndent();
                    context.writeToken(token);
                    context.writeNewLine();
                }
            )
        );
        //Commands for WRITING_LINE STATE
        commands.putAll(Map.of(
                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Char"), (token, context) -> { context.writeToken(token); },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Quotes"), (token, context) -> { context.writeToken(token); },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "for"), (token, context) -> { context.writeToken(token); },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Whitespace"), (token, context) -> { context.writeToken(token); },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Semicolon"), (token, context) -> { context.writeToken(token); context.writeNewLine(); },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Opening Curly Bracket"), (token, context) -> {
                    context.writeToken(token);
                    context.writeNewLine();
                    context.increaseIndentation();
                },

                Pair.of(FormatterState.of(FormatterState.WRITING_LINE), "Closing Curly Bracket"), (token, context) -> {
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

        FormatterCommand commandToReturn = commands.get(Pair.of(formatterState, token.getName()));

        if (commandToReturn == null && stateIsStringLiteral(formatterState)) {
            return defaultStringLiteralCommand;
        }

        if (commandToReturn == null && stateIsMultilineCommentEnd(formatterState)) {
            return defaultMultilineCommentEndCommand;
        }

        if (commandToReturn == null) {

            return (commandToken, context) -> { context.writeIndent(); context.writeToken(commandToken);};
        }

        return commandToReturn;
    }

    private boolean stateIsStringLiteral(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.WRITING_STRING_LITERAL);
    }

    private boolean stateIsMultilineCommentEnd(FormatterState formatterState) {

        return formatterState.getState().equals(FormatterState.MULTILINE_COMMENT_END_2);
    }

}
