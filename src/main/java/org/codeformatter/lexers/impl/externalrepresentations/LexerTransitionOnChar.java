package org.codeformatter.lexers.impl.externalrepresentations;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for a lexer state transition.
 * EXAMPLE:
 * - ch: 'f'
 *   stateToTransferTo: 'for_1'
 */
@Data
@NoArgsConstructor
public class LexerTransitionOnChar {

    private String ch;

    private String stateToTransferTo;

}
