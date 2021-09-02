package org.codeformatter.lexers;

public interface LexerCommand {

    void execute(Character character, LexerContext lexerContext);

}
