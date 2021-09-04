package org.codeformatter.lexers.impl.commands;

import static org.codeformatter.tokens.LexicalConstants.CARRIAGE_RETURN;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

public class WriteCarriageReturnCommand implements LexerCommand {
    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(CARRIAGE_RETURN);
    }
}
