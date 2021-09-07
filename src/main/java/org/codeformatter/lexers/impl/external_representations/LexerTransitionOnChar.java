package org.codeformatter.lexers.impl.external_representations;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representation for a lexer state transition.
 *
 * EXAMPLE:
 * - ch: 'f'
 *   stateToTransferTo: 'for_1'
 */
@Data
@NoArgsConstructor
@Setter
@Getter
public class LexerTransitionOnChar {

    private String ch;

    private String stateToTransferTo;

}