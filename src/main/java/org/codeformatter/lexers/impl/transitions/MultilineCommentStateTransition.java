package org.codeformatter.lexers.impl.transitions;

import static org.codeformatter.lexers.impl.State.StateName.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.tokens.LexicalConstants.MULTILINE_COMMENT_END;
import static org.codeformatter.utils.PredicateUtil.any;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;

public class MultilineCommentStateTransition extends StateTransition {

    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {
                ifToken(token -> token.getLexeme().endsWith(MULTILINE_COMMENT_END))
                        .thenState(() -> State.of(TERMINATED)),

                ifToken(any())
                        .thenState(() -> State.of(MULTILINE_COMMENT))
        };
    }

}
