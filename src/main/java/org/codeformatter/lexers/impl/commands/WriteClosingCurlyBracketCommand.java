package org.codeformatter.lexers.impl.commands;

import static org.codeformatter.tokens.LexicalConstants.CLOSING_CURLY_BRACKET;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

public class WriteClosingCurlyBracketCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(CLOSING_CURLY_BRACKET);
    }

}
