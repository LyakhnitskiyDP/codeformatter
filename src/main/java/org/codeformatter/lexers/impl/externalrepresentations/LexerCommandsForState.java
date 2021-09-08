package org.codeformatter.lexers.impl.externalrepresentations;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
public class LexerCommandsForState {

    private String state;

    private List<LexerCommandOnChar> commands;

}
