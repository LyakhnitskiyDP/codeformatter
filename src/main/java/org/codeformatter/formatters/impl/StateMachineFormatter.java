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

            log.debug("Formatter State: {} Current token to format: {}", state, token);
            FormatterCommand command = commandRepository.getCommand(state, token);
            command.execute(token, context);

            state = transitions.nextState(state, token);
            log.debug("Formatter state after transition: {}", state);
        }

    }

    private boolean stateIsTerminated(FormatterState state) {

        return state.getState().equals(FormatterState.TERMINATED);
    }

    @RequiredArgsConstructor
    private class StateMachineFormatterContext implements FormatterContext {
        private final int NUMBER_OF_SPACES_PER_TAB = 4;

        private int currentBlockLevel = 0;

        private final Writer writer;

        @Override
        public void writeToken(Token token) {

            System.out.println("Writing token: " + token);
            for (char ch : token.getLexeme().toCharArray()) {
                writer.writeChar(ch);
            }
        }

        @Override
        public void writeNewLine() {

            System.out.println("Writing new line");
            for (char ch : System.lineSeparator().toCharArray()) {
                writer.writeChar(ch);
            }
        }

        @Override
        public void writeIndent() {

            for (int i = 0; i < currentBlockLevel * NUMBER_OF_SPACES_PER_TAB; i++) {
                writer.writeChar(' ');
            }
        }

        @Override
        public void increaseIndentation() {

            currentBlockLevel++;
        }

        @Override
        public void decreaseIndentation() {

            currentBlockLevel--;
        }
    }

}
