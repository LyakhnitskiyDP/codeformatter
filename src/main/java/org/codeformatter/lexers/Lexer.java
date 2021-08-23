package org.codeformatter.lexers;

public interface Lexer {

    boolean hasMoreTokens();

    Token readToken();

}
