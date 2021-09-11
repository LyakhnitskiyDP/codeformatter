package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for a formatter action on a token.
 * EXAMPLE:
 * - tokenName: 'Slash'
 *   commandName: 'Start of a multiline comment 1'
 *   stateToTransferTo: 'initial'
 */
@Data
@NoArgsConstructor
public class FormatterActionOnToken {

    private String tokenName;

    private String commandName;

    private String stateToTransferTo;

}
