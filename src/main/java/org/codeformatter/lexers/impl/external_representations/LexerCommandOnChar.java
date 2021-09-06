package org.codeformatter.lexers.impl.external_representations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LexerCommandOnChar {

    String ch;

    String commandName;

}
