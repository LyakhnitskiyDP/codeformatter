package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representation for a formatter state transition.
 * EXAMPLE:
 * - tokenName: 'Whitespace'
 *   stateToTransferTo: 'initial'
 */
@Data
@NoArgsConstructor
@Setter
@Getter
public class FormatterTransitionOnToken {

    private String tokenName;

    private String stateToTransferTo;

}
