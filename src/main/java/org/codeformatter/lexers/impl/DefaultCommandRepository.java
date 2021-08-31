package org.codeformatter.lexers.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.Command;
import org.codeformatter.lexers.CommandRepository;

import java.util.HashMap;
import java.util.Map;

public class DefaultCommandRepository implements CommandRepository {

    private static final String defaultTokenName = "Char";

    private static final Map<Pair<State, Character>, Command> commandMap = new HashMap<>();

    static {

        commandMap.putAll(Map.of(
            Pair.of(State.of(State.INITIAL), ' '), (context) -> { context.appendLexeme(' '); context.setTokenName("Whitespace"); },

            Pair.of(State.of(State.INITIAL), ';'), (context) -> { context.appendLexeme(';'); context.setTokenName("Semicolon"); },
            Pair.of(State.of(State.INITIAL), '{'), (context) -> { context.appendLexeme('{'); context.setTokenName("Opening Curly Bracket"); },
            Pair.of(State.of(State.INITIAL), '}'), (context) -> { context.appendLexeme('}'); context.setTokenName("Closing Curly Bracket"); },
            Pair.of(State.of(State.INITIAL), '/'), (context) -> { context.appendLexeme('/'); context.setTokenName("Slash"); },
            Pair.of(State.of(State.INITIAL), '*'), (context) -> { context.appendLexeme('*'); context.setTokenName("Star"); },
            Pair.of(State.of(State.INITIAL), '\\'), (context) -> { context.appendLexeme('\\'); context.setTokenName("Backslash"); },
            Pair.of(State.of(State.INITIAL), '\"'), (context) -> { context.appendLexeme('\"'); context.setTokenName("Quotes"); }
        ));

        commandMap.putAll(Map.of(
            Pair.of(State.of(State.INITIAL), 'f'), (context) -> { context.appendLexeme('f'); context.setTokenName("for"); },
            Pair.of(State.of("for_1"), 'o'), (context) -> { context.appendLexeme('o'); context.setTokenName("for"); },
            Pair.of(State.of("for_2"), 'r'), (context) -> { context.appendLexeme('r'); context.setTokenName("for"); },
            Pair.of(State.of("for"), ')'), (context) -> { context.appendLexeme(')'); context.setTokenName("for"); }
        ));
    }


    @Override
    public Command getCommand(State state, char ch) {

        Command commandToReturn = commandMap.get(Pair.of(state, ch));

        if (commandToReturn == null && state.equals(State.of("for"))) {
            return context -> { context.appendLexeme(ch); context.setTokenName("for"); };
        }

        if (commandToReturn == null) {
            return (context -> { context.appendLexeme(ch); context.setTokenName(defaultTokenName); });
        }

        return commandToReturn;
    }

}
