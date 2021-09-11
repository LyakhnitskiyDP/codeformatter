package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class representation for formatter state actions.
 * EXAMPLE:
 * - state: 'initial'
 *   actions:
 *     - tokenName: null
 *       commandName: 'NoOpCommand'
 *       stateToTransferTo: 'writing line'
 *     - tokenName: 'Slash'
 *       commandName: 'Start of a multiline comment 1'
 *       stateToTransferTo: 'initial'
 */
@Data
@NoArgsConstructor
public class FormatterActionsForState {

    private String state;

    private List<FormatterActionOnToken> actions;

}
