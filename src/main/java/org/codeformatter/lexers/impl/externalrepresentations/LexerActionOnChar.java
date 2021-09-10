package org.codeformatter.lexers.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for a lexer action for a state.
 * EXAMPLE:
 * - ch: 'f'
 *   commandName: 'WriteCharCommand'
 *   stateToTransferTo: 'for_1'
 */
@Data
@NoArgsConstructor
public class LexerActionOnChar {

    private String ch;

    private String commandName;

    private String stateToTransferTo;

}
