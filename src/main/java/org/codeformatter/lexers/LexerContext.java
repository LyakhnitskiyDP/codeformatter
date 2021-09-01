package org.codeformatter.lexers;

import org.codeformatter.tokens.Token;

public interface LexerContext {

    void appendLexeme(char ch);

    void appendLexemePostpone(char ch);

    void setTokenName(String name);

    Token completeToken();
}
