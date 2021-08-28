package org.codeformatter.lexers.impl.transitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.codeformatter.lexers.impl.State.StateName.SIMPLE_TEXT;
import static org.codeformatter.lexers.impl.State.StateName.TEXT_BLOCK;

import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TextBlockStateTransitionTest {
    TextBlockStateTransition textBlockStateTransition;

    @BeforeEach
    public void setup() {

        this.textBlockStateTransition = new TextBlockStateTransition();
    }

    @Test
    public void should_stay_in_the_same_state_when_not_finished() {

        Token unfinishedTextBlock = new DefaultToken("", "\"\"\" uncompleted text block");

        State changedState = textBlockStateTransition.apply(unfinishedTextBlock);
        State expectedState = State.of(TEXT_BLOCK);

        assertEquals(changedState, expectedState);
    }

    @Test
    public void should_change_state_to_simple_text() {

        Token token = new DefaultToken("", "\"\"\" complete text block \"\"\"");

        State changedState = textBlockStateTransition.apply(token);
        State expectedState = State.of(SIMPLE_TEXT);

        assertEquals(changedState, expectedState);
    }


    @Test
    public void should_recognize_completed_text_block() {

        Token completedTextBlockToken = new DefaultToken("", "\"\"\" completed text block \"\"\"");

        assertTrue(textBlockStateTransition.textBlockIsEnded(completedTextBlockToken));
    }

    @Test
    public void should_recognize_uncompleted_text_block() {

        Token uncompletedTextBlockToken = new DefaultToken("", "\"\"\" uncompleted text ");

        assertFalse(textBlockStateTransition.textBlockIsEnded(uncompletedTextBlockToken));
    }



}
