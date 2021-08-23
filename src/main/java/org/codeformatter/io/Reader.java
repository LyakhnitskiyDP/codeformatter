package org.codeformatter.io;

import java.util.Iterator;

public interface Reader extends Iterator<Character> {

    char readChar();

    boolean hasMoreChars();

}
