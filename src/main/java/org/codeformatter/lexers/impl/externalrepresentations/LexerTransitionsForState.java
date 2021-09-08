package org.codeformatter.lexers.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
public class LexerTransitionsForState {

    private String state;

    private List<LexerTransitionOnChar> transitions;
}
