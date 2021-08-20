package org.codeformatter.formatters;

import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;

public interface Formatter {

    void format(Reader reader, Writer writer);

}
