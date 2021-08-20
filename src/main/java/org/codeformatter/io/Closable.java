package org.codeformatter.io;

import org.codeformatter.exceptions.CloseException;

public interface Closable extends AutoCloseable {

    @Override
    void close() throws CloseException;

}
