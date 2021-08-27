package org.codeformatter.lexers.impl.transitions;


import static org.codeformatter.lexers.impl.State.StateName.FOR_LOOP;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;
import static org.codeformatter.utils.PredicateUtil.any;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;

public class ForLoopStateTransition extends StateTransition {

    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {
                ifToken(token -> token.getLexeme().endsWith(OPENING_CURLY_BRACKET))
                        .thenState(() -> State.of(TERMINATED)),

                ifToken(any())
                        .thenState(() -> State.of(FOR_LOOP))
        };
    }

}
