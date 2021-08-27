package org.codeformatter.formatters.impl;

import java.util.Map;
import java.util.function.Consumer;
import org.codeformatter.formatters.Formatter;
import org.codeformatter.formatters.WriterCommand;
import org.codeformatter.io.Writer;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.tokens.Token;
import org.codeformatter.utils.StringUtil;

public class StateMachineFormatter implements Formatter {

    private static final int NUMBER_OF_SPACES_PER_TAB = 4;

    private static final Map<Character, Consumer<Context>> contextTransitions = Map.of(
            '{', Context::increaseBlockLevel,
            '}', Context::decreaseBlockLevel
    );

    @Override
    public void format(Lexer lexer, Writer writer) {

        Context context = new Context();
        while (lexer.hasMoreTokens()) {
            Token token = lexer.readToken();
            WriterCommand writerCommand = getWriterCommand(context, token);
            writerCommand.execute(writer);

            char lastChar = token.getLexeme().charAt(token.getLexeme().length() - 1);
            contextTransitions.getOrDefault(lastChar, ctx -> {})
                    .accept(context);
        }

    }

    private WriterCommand getWriterCommand(Context context, Token token) {
        return (writer) -> {
            StringBuilder formattedLexeme = new StringBuilder();
            int numberOfPrecedingSpaces = context.getBlockLevel() * NUMBER_OF_SPACES_PER_TAB;

            formattedLexeme.append(StringUtil.trimAndIndent(token.getLexeme(), numberOfPrecedingSpaces))
                           .append(System.lineSeparator());

            for (char ch : formattedLexeme.toString().toCharArray()) {
                writer.writeChar(ch);
            }
        };
    }

}
