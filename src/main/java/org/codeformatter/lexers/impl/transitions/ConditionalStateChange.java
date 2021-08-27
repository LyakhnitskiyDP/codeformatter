package org.codeformatter.lexers.impl.transitions;

import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;

@RequiredArgsConstructor(staticName = "on")
public class ConditionalStateChange {

    private final Predicate<Token> condition;

    private final Supplier<State> changedState;

    public State getState() {

        return changedState.get();
    }

    public boolean conditionIsMet(Token token) {

        return condition.test(token);
    }

    public static StateChangeConditionBuilder ifToken(Predicate<Token> condition) {
        StateChangeConditionBuilder builder = new StateChangeConditionBuilder();
        builder.setCondition(condition);
        return builder;
    }

    public static class StateChangeConditionBuilder {
        private Predicate<Token> condition;

        StateChangeConditionBuilder() {
        }

        public void setCondition(Predicate<Token> condition) {
            this.condition = condition;
        }

        public ConditionalStateChange thenState(Supplier<State> changedState) {
            return new ConditionalStateChange(condition, changedState);
        }
    }
}
