package org.codeformatter.lexers.impl.external_representations;

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

    private String state;

    private List<LexerCommandOnChar> commands;

}
