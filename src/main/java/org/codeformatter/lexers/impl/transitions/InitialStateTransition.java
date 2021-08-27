package org.codeformatter.lexers.impl.transitions;

import static java.util.Arrays.stream;
import static org.codeformatter.lexers.impl.State.StateName.FOR_LOOP;
import static org.codeformatter.lexers.impl.State.StateName.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.State.StateName.SIMPLE_TEXT;
import static org.codeformatter.lexers.impl.State.StateName.SINGLE_LINE_COMMENT;
import static org.codeformatter.lexers.impl.State.StateName.STRING_LITERAL;
import static org.codeformatter.lexers.impl.State.StateName.TERMINATED;
import static org.codeformatter.lexers.impl.transitions.ConditionalStateChange.ifToken;
import static org.codeformatter.tokens.LexicalConstants.FOR_LOOP_START;
import static org.codeformatter.tokens.LexicalConstants.MULTILINE_COMMENT_START;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.tokens.LexicalConstants.SINGLE_LINE_COMMENT_START;
import static org.codeformatter.tokens.LexicalConstants.lineTerminatingChars;

import org.codeformatter.lexers.StateTransition;
import org.codeformatter.lexers.impl.State;
import org.codeformatter.tokens.Token;

public class InitialStateTransition extends StateTransition {

    @Override
    protected ConditionalStateChange[] getConditionalStateChanges() {
        return new ConditionalStateChange[] {
                ifToken(token -> token.getLexeme().endsWith(QUOTES))
                        .thenState(() -> State.of(STRING_LITERAL)),

                ifToken(token -> token.getLexeme().trim().startsWith(MULTILINE_COMMENT_START))
                        .thenState(() -> State.of(MULTILINE_COMMENT)),

                ifToken(token -> token.getLexeme().trim().startsWith(SINGLE_LINE_COMMENT_START))
                        .thenState(() -> State.of(SINGLE_LINE_COMMENT)),

                ifToken(token -> token.getLexeme().trim().equals(FOR_LOOP_START))
                        .thenState(() -> State.of(FOR_LOOP)),

                ifToken(InitialStateTransition::endsWithLineTerminatingChar)
                        .thenState(() -> State.of(TERMINATED)),

                ifToken(token -> true)
                        .thenState(() -> State.of(SIMPLE_TEXT))
        };
    }

    private static boolean endsWithLineTerminatingChar(Token token) {

        return stream(lineTerminatingChars)
                .anyMatch(terminator -> token.getLexeme().endsWith(terminator));
    }

}
