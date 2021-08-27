package org.codeformatter.formatters;

import org.codeformatter.io.Writer;

public interface WriterCommand {

    void execute(Writer writer);

}
