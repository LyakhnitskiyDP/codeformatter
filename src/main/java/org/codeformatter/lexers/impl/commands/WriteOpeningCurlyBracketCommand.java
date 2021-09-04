package org.codeformatter.lexers.impl.commands;

import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

public class WriteOpeningCurlyBracketCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(OPENING_CURLY_BRACKET);
    }
}
