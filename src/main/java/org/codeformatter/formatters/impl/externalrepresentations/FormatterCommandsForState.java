package org.codeformatter.formatters.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for external commands for a state.
 * EXAMPLE:
 * - state: 'initial'
 *   commands:
 *     - tokenName: null
 *       commandName: 'NoOpCommand'
 *     - tokenName: 'Slash'
 *       commandName: 'IndentAndWriteCommand'
 */
@Data
@NoArgsConstructor
public class FormatterCommandsForState {

    private String state;

    private List<FormatterCommandOnToken> commands;

}
