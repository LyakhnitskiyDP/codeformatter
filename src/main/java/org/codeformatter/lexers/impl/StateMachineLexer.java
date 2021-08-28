package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.State.StateName.INITIAL;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.io.Reader;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;

@RequiredArgsConstructor
@Slf4j
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

            log.debug("State before change is: {}", state.getName());
            log.debug("Current token is: {}", token.getLexeme().replaceAll(System.lineSeparator(), "").trim());

            commandFactory.getCommend(token).execute();

            log.debug("State after change is: {}", state.getName());
            log.debug("*********************************");
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
