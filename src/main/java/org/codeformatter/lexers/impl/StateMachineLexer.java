package org.codeformatter.lexers.impl;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringBuilderReader;
import org.codeformatter.io.string.StringWriter;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;
import org.codeformatter.lexers.LexerStateTransitions;
import org.codeformatter.lexers.TokenBuilder;
import org.codeformatter.tokens.Token;

@Slf4j
public class StateMachineLexer implements Lexer {

    private Writer postponeWriter;
    private Reader postponeReader;

    private LexerCommandRepository lexerCommandRepository = new DefaultLexerCommandRepository();
    private LexerStateTransitions lexerStateTransitions = new DefaultLexerStateTransitions();
    private org.codeformatter.lexers.LexerContext lexerContext = new LexerLexerContext();

    private Reader reader;

    public StateMachineLexer(Reader reader) {
        this.reader = reader;

        StringBuilder postponeBuffer = new StringBuilder();
        postponeWriter = new StringWriter(postponeBuffer);
        postponeReader = new StringBuilderReader(postponeBuffer);
    }

    public Token nextToken() {

        LexerState lexerState = LexerState.of(LexerState.INITIAL);

        while (postponeReader.hasMoreChars() && stateIsNotTerminated(lexerState)) {
            lexerState = makeStep(lexerState, postponeReader);
        }

        while (reader.hasMoreChars() && stateIsNotTerminated(lexerState)) {
            lexerState = makeStep(lexerState, reader);
        }

        return buildToken();
    }

    private boolean stateIsNotTerminated(LexerState lexerState) {

        return ! lexerState.getState().equals(LexerState.TERMINATED);
    }

    private LexerState makeStep(LexerState lexerState, Reader reader) {
        char ch = reader.readChar();

        log.debug("Lexer state before: {} current char: {}", lexerState, ch);
        LexerCommand lexerCommand = lexerCommandRepository.getCommand(lexerState, ch);
        lexerCommand.execute(ch, lexerContext);

        LexerState lexerStateToReturn = lexerStateTransitions.nextState(lexerState, ch);

        log.debug("Lexer state after: {}", lexerStateToReturn);
        return lexerStateToReturn;
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

    private class LexerLexerContext implements org.codeformatter.lexers.LexerContext {

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
