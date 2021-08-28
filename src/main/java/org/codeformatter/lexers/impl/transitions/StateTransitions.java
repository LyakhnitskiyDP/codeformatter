package org.codeformatter.lexers.impl.transitions;


import java.util.Map;
import java.util.function.Function;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;

public class StateTransitions {

    private static final Map<State, Function<Token, State>> transitions;

    static {

        transitions = Map.of(
                State.of(State.StateName.INITIAL), new InitialStateTransition(),

                State.of(State.StateName.TEXT_BLOCK), new TextBlockStateTransition(),

                State.of(State.StateName.SIMPLE_TEXT), new SimpleTextStateTransition(),

                State.of(State.StateName.FOR_LOOP), new ForLoopStateTransition(),

                State.of(State.StateName.STRING_LITERAL), new StringLiteralStateTransition(),

                State.of(State.StateName.MULTILINE_COMMENT), new MultilineCommentStateTransition(),

                State.of(State.StateName.SINGLE_LINE_COMMENT), new SingleLineCommentTransition()
        );

    }

    public State nextState(State currentState, Token currentToken) {

        return transitions.get(currentState)
                          .apply(currentToken);
    }

}
