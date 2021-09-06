package org.codeformatter.lexers.impl;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LexerCommandsForState {

    String state;

    List<LexerCommandOnChar> commands;

}
