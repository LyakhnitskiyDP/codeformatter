package org.codeformatter.tokens.impl;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codeformatter.tokens.Token;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DefaultToken implements Token {

    private String name;

    private String lexeme;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLexeme() {
        return this.lexeme;
    }
}
