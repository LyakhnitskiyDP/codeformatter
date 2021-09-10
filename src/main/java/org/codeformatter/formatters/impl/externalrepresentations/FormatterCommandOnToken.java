package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for an external formatter command.
 * EXAMPLE:
 * - tokenName: 'Slash'
 *   commandName: 'IndentAndWriteCommand'
 */
@Data
@NoArgsConstructor
public class FormatterCommandOnToken {

    String tokenName;

    String commandName;

}
