package org.codeformatter.formatters.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.formatters.Formatter;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.formatters.FormatterContext;
import org.codeformatter.formatters.FormatterStateTransitions;
import org.codeformatter.io.Writer;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.tokens.Token;

@Slf4j
public class StateMachineFormatter implements Formatter {

    private final FormatterStateTransitions transitions = new DefaultFormatterStateTransitions();
    private final FormatterCommandRepository commandRepository = new DefaultFormatterCommandRepository();

    @Override
    public void format(Lexer lexer, Writer writer) {

        FormatterState state = FormatterState.of(FormatterState.INITIAL);
        FormatterContext context = new StateMachineFormatterContext(writer);

        while (lexer.hasMoreTokens() && ! stateIsTerminated(state)) {

            Token token = lexer.readToken();

            FormatterCommand command = commandRepository.getCommand(state, token);
            command.execute(token, context);

            state = transitions.nextState(state, token);
        }

    }

    private boolean stateIsTerminated(FormatterState state) {

        return state.getState().equals(FormatterState.TERMINATED);
    }

    @RequiredArgsConstructor
    private class StateMachineFormatterContext implements FormatterContext {
        private final int numberOfSpacesPerTab = 4;

        private int currentBlockLevel = 0;

        private final Writer writer;

        @Override
        public void writeToken(Token token) {

            log.trace("Writing token: {}", token);
            for (char ch : token.getLexeme().toCharArray()) {
                writer.writeChar(ch);
            }
        }

        @Override
        public void writeNewLine() {

            log.trace("Writing new line");
            for (char ch : System.lineSeparator().toCharArray()) {
                writer.writeChar(ch);
            }
        }

        @Override
        public void writeIndent() {

            log.trace("Writing indentation");
            for (int i = 0; i < currentBlockLevel * numberOfSpacesPerTab; i++) {
                writer.writeChar(' ');
            }
        }

        @Override
        public void increaseIndentation() {

            log.trace("Increasing block level");
            currentBlockLevel++;
        }

        @Override
        public void decreaseIndentation() {

            log.trace("Decreasing block level");
            currentBlockLevel--;
        }
    }

}
