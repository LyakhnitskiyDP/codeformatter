package org.codeformatter.lexers.impl.commands;

import static org.codeformatter.tokens.LexicalConstants.QUOTES;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

public class WriteQuotesCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(QUOTES);
    }

}
