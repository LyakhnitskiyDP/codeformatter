package org.codeformatter.lexers.impl.external_representations;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class LexerTransitionOnChar {

    private String ch;

    private String stateToTransferTo;

}