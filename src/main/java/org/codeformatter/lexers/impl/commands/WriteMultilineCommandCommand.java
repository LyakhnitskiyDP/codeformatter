package org.codeformatter.lexers.impl.commands;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;

public class WriteMultilineCommandCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(MULTILINE_COMMENT);
    }
}
