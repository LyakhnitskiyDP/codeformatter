package org.codeformatter.lexers.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representation for lexer commands.
 * EXAMPLE:
 * - state: "initial"
 *   commands:
 *     - ch: null
 *       commandName: 'WriteCharCommand'
 *     - ch: ';'
 *       commandName: 'WriteSemicolonCommand'
 */
@Data
@NoArgsConstructor
public class LexerCommandsForState {

    private String state;

    private List<LexerCommandOnChar> commands;

}
