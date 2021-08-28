package org.codeformatter.lexers.impl.transitions;

import static org.codeformatter.lexers.impl.State.StateName.SIMPLE_TEXT;
import static org.codeformatter.lexers.impl.State.StateName.TEXT_BLOCK;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.tokens.LexicalConstants.TRIPLE_QUOTES;
import static org.codeformatter.utils.PredicateUtil.any;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;

public class TextBlockStateTransition extends StateTransition {

    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {
                ifToken(this::textBlockIsEnded)
                        .thenState(() -> State.of(SIMPLE_TEXT)),

                ifToken(any())
                        .thenState(() -> State.of(TEXT_BLOCK)),
        };
    }

    boolean textBlockIsEnded(Token token) {


        return token.getLexeme().length() >= 6 && token.getLexeme().endsWith(TRIPLE_QUOTES);
    }

}
