package org.codeformatter.lexers.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class representation for lexer actions for a state.
 * EXAMPLE:
 * - state: "initial"
 *   actions:
 *     - ch: null
 *       commandName: 'WriteCharCommand'
 *       stateToTransferTo: 'terminated'
 *     - ch: 'f'
 *       commandName: 'WriteCharCommand'
 *       stateToTransferTo: 'for_1'
 */
@Data
@NoArgsConstructor
public class LexerActionsForState {

    private String state;

    private List<LexerActionOnChar> actions;

}
