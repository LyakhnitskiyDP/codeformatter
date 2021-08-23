package org.codeformatter.formatters;

import org.codeformatter.io.Writer;
import org.codeformatter.lexers.Lexer;

public interface Formatter {

    void format(Lexer lexer, Writer writer);

}
