package org.codeformatter.io;

public interface Reader extends Closable {

    char readChar();

    boolean hasMoreChars();

}
