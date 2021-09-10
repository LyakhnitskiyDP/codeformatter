package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for a formatter state transition.
 * EXAMPLE:
 * - tokenName: 'Whitespace'
 *   stateToTransferTo: 'initial'
 */
@Data
@NoArgsConstructor
public class FormatterTransitionOnToken {

    private String tokenName;

    private String stateToTransferTo;

}
