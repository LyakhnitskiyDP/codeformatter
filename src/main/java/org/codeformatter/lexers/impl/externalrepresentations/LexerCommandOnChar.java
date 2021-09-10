package org.codeformatter.lexers.impl.externalrepresentations;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for a lexer command.
 * EXAMPLE:
 * - ch: ';'
 *   commandName: 'WriteSemicolonCommand'
 */
@Data
@NoArgsConstructor
public class LexerCommandOnChar {

    String ch;

    String commandName;

}
