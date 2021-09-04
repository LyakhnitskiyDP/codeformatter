package org.codeformatter.lexers.impl.commands;

import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;

import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerContext;

public class WriteMultilineCommentCommand implements LexerCommand {

    @Override
    public void execute(Character character, LexerContext lexerContext) {
        lexerContext.appendLexeme(character);
        lexerContext.setTokenName(MULTILINE_COMMENT);
    }
}
