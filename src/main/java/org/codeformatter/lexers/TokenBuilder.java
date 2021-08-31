package org.codeformatter.lexers;

import lombok.Setter;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;

public class TokenBuilder {

    private final StringBuilder lexeme = new StringBuilder();

    @Setter
    private String name;

    public void appendLexeme(char ch) {
        lexeme.append(ch);
    }

    public Token getToken() {
        return new DefaultToken(name, lexeme.toString());
    }
}
