package org.codeformatter.lexers.impl.transitions;

import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.codeformatter.lexers.impl.State.StateName.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SimpleTextStateTransitionTest {

    private SimpleTextStateTransition simpleTextStateTransition;

    @BeforeEach
    public void setup() {

        this.simpleTextStateTransition = new SimpleTextStateTransition();
    }

    @Test
    public void should_transition_to_text_block_state() {

        Token textBlockToken = new DefaultToken("", "\"\"\"");

        testTransition(textBlockToken, State.of(TEXT_BLOCK));
    }

    @Test
    public void should_transition_to_string_literal_state() {

        Token textBlockToken = new DefaultToken("", "\"");

        testTransition(textBlockToken, State.of(STRING_LITERAL));
    }

    @Test
    public void should_transition_to_multiline_comment_state() {

        Token textBlockToken = new DefaultToken("", "/*");

        testTransition(textBlockToken, State.of(MULTILINE_COMMENT));
    }

    @Test
    public void should_transition_to_single_line_comment_state() {

        Token textBlockToken = new DefaultToken("", "//");

        testTransition(textBlockToken, State.of(SINGLE_LINE_COMMENT));
    }


    @Test
    public void should_transition_to_for_loop_state() {

        Token textBlockToken = new DefaultToken("", "for (");

        testTransition(textBlockToken, State.of(FOR_LOOP));
    }


    @Test
    public void should_transition_to_terminated_state() {

        Token textBlockToken = new DefaultToken("", "statement;");

        testTransition(textBlockToken, State.of(TERMINATED));
    }

    private void testTransition(Token token, State expectedState) {

        State changedState = simpleTextStateTransition.apply(token);

        assertEquals(changedState, expectedState);
    }

}
