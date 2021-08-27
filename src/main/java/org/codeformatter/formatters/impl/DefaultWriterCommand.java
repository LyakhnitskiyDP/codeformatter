package org.codeformatter.formatters.impl;

import lombok.RequiredArgsConstructor;
import org.codeformatter.formatters.WriterCommand;
import org.codeformatter.io.Writer;
import org.codeformatter.tokens.Token;

@RequiredArgsConstructor
public class DefaultWriterCommand implements WriterCommand {

    private final Context context;

    private final Token token;

    @Override
    public void execute(Writer writer) {

    }
}
