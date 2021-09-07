package org.codeformatter.formatters.impl.external_representations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class representation for external commands for a state.
 *
 * EXAMPLE:
 *
 * - state: 'initial'
 *   commands:
 *     - tokenName: null
 *       commandName: 'NoOpCommand'
 *     - tokenName: 'Slash'
 *       commandName: 'IndentAndWriteCommand'
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class FormatterCommandsForState {

    private String state;

    private List<FormatterCommandOnToken> commands;

}
