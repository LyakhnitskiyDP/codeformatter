package org.codeformatter.lexers.impl;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringBuilderReader;
import org.codeformatter.io.string.StringWriter;
import org.codeformatter.lexers.Command;
import org.codeformatter.lexers.CommandRepository;
import org.codeformatter.lexers.Context;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.StateTransitions;
import org.codeformatter.lexers.TokenBuilder;
import org.codeformatter.tokens.Token;

@Slf4j
public class StateMachineLexer implements Lexer {

    private Writer postponeWriter;
    private Reader postponeReader;

    private CommandRepository commandRepository = new DefaultCommandRepository();
    private StateTransitions stateTransitions = new DefaultStateTransitions();
    private Context lexerContext = new LexerContext();

    private Reader reader;

    public StateMachineLexer(Reader reader) {
        this.reader = reader;

        StringBuilder postponeBuffer = new StringBuilder();
        postponeWriter = new StringWriter(postponeBuffer);
        postponeReader = new StringBuilderReader(postponeBuffer);
    }

    public Token nextToken() {

        State state = State.of(State.INITIAL);

        while (postponeReader.hasMoreChars() && !stateIsTerminated(state)) {
            state = makeStep(state, postponeReader);
        }

        while (reader.hasMoreChars() && !stateIsTerminated(state)) {
            state = makeStep(state, reader);
        }

        return buildToken();
    }

    private boolean stateIsTerminated(State state) {

        return state.getState().equals(State.TERMINATED);
    }

    private State makeStep(State state, Reader reader) {
        char ch = reader.readChar();

        log.debug("Lexer state before: {} current char: {}", state, ch);
        Command command = commandRepository.getCommand(state, ch);
        command.execute(lexerContext);

        State stateToReturn = stateTransitions.nextState(state, ch);

        log.debug("Lexer state after: {}", stateToReturn);
        return stateToReturn;
    }

    private Token buildToken() {

        return lexerContext.completeToken();
    }

    @Override
    public boolean hasMoreTokens() {
        return reader.hasMoreChars();
    }

    @Override
    public Token readToken() {
        return nextToken();
    }

    private class LexerContext implements Context {

        private TokenBuilder tokenBuilder = new TokenBuilder();

        @Override
        public void appendLexeme(char ch) {

            tokenBuilder.appendLexeme(ch);
        }

        @Override
        public void appendLexemePostpone(char ch) {

            postponeWriter.writeChar(ch);
        }

        @Override
        public void setTokenName(String name) {

            tokenBuilder.setName(name);
        }

        @Override
        public Token completeToken() {

            Token tokenToReturn = tokenBuilder.getToken();

            this.tokenBuilder = new TokenBuilder();

            return tokenToReturn;
        }
    }
}
