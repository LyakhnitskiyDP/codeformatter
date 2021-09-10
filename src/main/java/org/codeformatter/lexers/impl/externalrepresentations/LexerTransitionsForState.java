package org.codeformatter.lexers.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for lexer state transitions.
 * EXAMPLE:
 * - state: 'initial'
 *   transitions:
 *     - ch: null
 *       stateToTransferTo: 'terminated'
 *     - ch: 'f'
 *       stateToTransferTo: 'for_1'
 */
@Data
@NoArgsConstructor
public class LexerTransitionsForState {

    private String state;

    private List<LexerTransitionOnChar> transitions;
}
