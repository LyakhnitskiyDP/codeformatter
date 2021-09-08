package org.codeformatter.formatters.impl.externalrepresentations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representation for an external formatter command.
 * EXAMPLE:
 * - tokenName: 'Slash'
 *   commandName: 'IndentAndWriteCommand'
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class FormatterCommandOnToken {

    String tokenName;

    String commandName;

}
