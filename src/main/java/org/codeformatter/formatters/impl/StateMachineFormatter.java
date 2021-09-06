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

    private final FormatterStateTransitions transitions;
    private final FormatterCommandRepository commandRepository;

    public StateMachineFormatter() {
        transitions = new ExternalizedFormatterStateTransitions("src/main/resources/FormatterStateTransitions.yaml");
        commandRepository = new ExternalizedFormatterCommandRepository("src/main/resources/FormatterCommands.yaml");
    }

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

}
