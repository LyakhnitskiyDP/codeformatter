package org.codeformatter.lexers.impl.transitions;

import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.codeformatter.lexers.impl.State.StateName.FOR_LOOP;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ForLoopStateTransitionTest {

    private ForLoopStateTransition forLoopStateTransition;

    @BeforeEach
    public void setup() {

        this.forLoopStateTransition = new ForLoopStateTransition();
    }

    @Test
    public void should_stay_in_current_state() {

        Token unfinishedForLoop = new DefaultToken("", "for (int i = 0; i <");

        State changedState = forLoopStateTransition.apply(unfinishedForLoop);
        State expectedState = State.of(FOR_LOOP);

        assertEquals(changedState, expectedState);
    }

    @Test
    public void should_change_state_to_terminated() {

        Token completedForLoop = new DefaultToken("", "for (int i = 0; i < 0;) {");

        State changedState = forLoopStateTransition.apply(completedForLoop);
        State expectedState = State.of(TERMINATED);

        assertEquals(changedState, expectedState);
    }

}
