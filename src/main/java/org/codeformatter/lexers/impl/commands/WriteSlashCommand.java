package org.codeformatter.lexers.impl.commands;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

import static org.codeformatter.tokens.LexicalConstants.SLASH;

public class WriteSlashCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(SLASH);
    }

}
