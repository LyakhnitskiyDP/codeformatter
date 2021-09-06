package org.codeformatter.lexers.impl;

import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;
import org.codeformatter.io.string.StringBuilderReader;
import org.codeformatter.io.string.StringWriter;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;
import org.codeformatter.lexers.LexerContext;
import org.codeformatter.lexers.LexerStateTransitions;
import org.codeformatter.tokens.Token;

public class StateMachineLexer implements Lexer {

    private final Reader postponeReader;

    private final LexerCommandRepository lexerCommandRepository;
    private final LexerStateTransitions lexerStateTransitions;
    private final LexerContext lexerContext;

    private final Reader reader;

    public StateMachineLexer(Reader reader) {
        this.reader = reader;

        lexerCommandRepository = new ExternalizedLexerCommandRepository("src/main/resources/LexerCommands.yaml");
        lexerStateTransitions = new ExternalizedLexerStateTransitions("src/main/resources/LexerStateTransitions.yaml");

        StringBuilder postponeBuffer = new StringBuilder();
        Writer postponeWriter = new StringWriter(postponeBuffer);
        postponeReader = new StringBuilderReader(postponeBuffer);

        this.lexerContext = new StateMachineLexerContext(postponeWriter);
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


        LexerCommand lexerCommand = lexerCommandRepository.getCommand(lexerState, ch);
        lexerCommand.execute(ch, lexerContext);

        return lexerStateTransitions.nextState(lexerState, ch);
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

}
