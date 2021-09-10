package org.codeformatter.formatters.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for formatter state transitions.
 * EXAMPLE:
 * - state: 'initial'
 *   transitions:
 *     - tokenName: null
 *       stateToTransferTo: 'writing line'
 *     - tokenName: 'Whitespace'
 *       stateToTransferTo: 'initial'
 */
@Data
@NoArgsConstructor
public class FormatterTransitionsForState {

    private String state;

    private List<FormatterTransitionOnToken> transitions;

}
