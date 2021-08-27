package org.codeformatter.lexers;

import org.codeformatter.tokens.Token;

public interface Lexer {

    boolean hasMoreTokens();

    Token readToken();

}
