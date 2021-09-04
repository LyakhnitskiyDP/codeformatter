package org.codeformatter.lexers.impl;

import org.codeformatter.io.Writer;
import org.codeformatter.lexers.LexerContext;
import org.codeformatter.tokens.Token;
import org.codeformatter.tokens.impl.DefaultToken;

class StateMachineLexerContext implements LexerContext {

    private final StringBuilder lexemeBuilder;
    private final Writer postponeWriter;
    private String tokenName;


    public StateMachineLexerContext(Writer postponeWriter) {
        this.postponeWriter = postponeWriter;
        this.lexemeBuilder = new StringBuilder();
    }

    @Override
    public void appendLexeme(char ch) {

        lexemeBuilder.append(ch);
    }

    @Override
    public void appendLexemePostpone(char ch) {

        postponeWriter.writeChar(ch);
    }

    @Override
    public void setTokenName(String name) {

        this.tokenName = name;
    }

    @Override
    public Token completeToken() {

        Token tokenToReturn = new DefaultToken(tokenName, lexemeBuilder.toString());

        lexemeBuilder.delete(0, lexemeBuilder.length());

        return tokenToReturn;
    }
}
