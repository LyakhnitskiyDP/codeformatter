package org.codeformatter.lexers.impl.externalrepresentations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representation for a lexer command.
 * EXAMPLE:
 * - ch: ';'
 *   commandName: 'WriteSemicolonCommand'
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class LexerCommandOnChar {

    String ch;

    String commandName;

}
