package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.State.StateName.INITIAL;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;

import lombok.RequiredArgsConstructor;
import org.codeformatter.io.Reader;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;

@RequiredArgsConstructor
public class StateMachineLexer implements Lexer {

    private final Reader reader;

    @Override
    public Token readToken() {

        final State state = State.of(INITIAL);
        Token token = new DefaultToken(INITIAL.name(), "");

        final CommandFactory commandFactory = new CommandFactory(state);

        while (reader.hasMoreChars() && ! stateIsTerminated(state)) {

            char ch = reader.readChar();
            token = new DefaultToken(
                    state.getName().toString(),
                    token.getLexeme() + ch
            );

            commandFactory.getCommend(token).execute();
        }

        return token;
    }

    private boolean stateIsTerminated(State state) {
        return state.getName().equals(TERMINATED);
    }

    @Override
    public boolean hasMoreTokens() {
        return reader.hasMoreChars();
    }

}
