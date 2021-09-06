package org.codeformatter.lexers.impl;

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
