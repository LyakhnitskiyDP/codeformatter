package org.codeformatter.lexers;

import org.codeformatter.lexers.impl.State;

public interface CommandRepository {

    Command getCommand(State state, char ch);

}
