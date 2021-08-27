package org.codeformatter.lexers.impl.transitions;


import static org.codeformatter.lexers.impl.State.StateName.SINGLE_LINE_COMMENT;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.utils.PredicateUtil.any;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;

public class SingleLineCommentTransition extends StateTransition {

    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {
                ifToken(token -> token.getLexeme().endsWith(System.lineSeparator()))
                        .thenState(() -> State.of(TERMINATED)),

                ifToken(any())
                        .thenState(() -> State.of(SINGLE_LINE_COMMENT))
        };
    }

}
