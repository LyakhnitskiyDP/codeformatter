package org.codeformatter.lexers.impl.external_representations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class representation for lexer commands.
 *
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
