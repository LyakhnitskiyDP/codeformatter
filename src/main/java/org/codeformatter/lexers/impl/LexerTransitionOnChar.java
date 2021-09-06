package org.codeformatter.lexers.impl;


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