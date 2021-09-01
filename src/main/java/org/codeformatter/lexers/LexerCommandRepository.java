package org.codeformatter.lexers;

import org.codeformatter.lexers.impl.LexerState;

public interface LexerCommandRepository {

    LexerCommand getCommand(LexerState lexerState, char ch);

}
