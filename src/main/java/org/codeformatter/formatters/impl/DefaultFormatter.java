package org.codeformatter.formatters.impl;

import org.codeformatter.formatters.Context;
import org.codeformatter.formatters.Formatter;
import org.codeformatter.io.Writer;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.Token;
import org.codeformatter.utils.StringUtil;

public class DefaultFormatter implements Formatter {

    private static final int NUMBER_OF_SPACES_PER_TAB = 4;

    private static final String OPENING_CURLY_BRACKET = "{";
    private static final String CLOSING_CURLY_BRACKET = "}";

    private final Context context = new Context();

    public void format(Lexer lexer, Writer writer) {

        while (lexer.hasMoreTokens()) {

            Token token = lexer.readToken();

            if (isLevelDecreasing(token)) {
                context.decreaseBlockLevel();
            }

            String formattedLexeme = formatLexeme(token);

            if (isLevelIncreasing(token)) {
                context.increaseBlockLevel();
            }

            for (char ch : formattedLexeme.toCharArray()) {
                writer.writeChar(ch);
            }
        }
    }

    private String formatLexeme(Token token) {

        StringBuilder formattedLexeme = new StringBuilder();
        int numberOfPrecedingSpaces = context.getBlockLevel() * NUMBER_OF_SPACES_PER_TAB;

        formattedLexeme.append(StringUtil.trimAndIndent(token.getLexeme(), numberOfPrecedingSpaces))
                       .append(System.lineSeparator());

        return formattedLexeme.toString();
    }

    private boolean isLevelIncreasing(Token token) {

        return token.getLexeme().endsWith(OPENING_CURLY_BRACKET);
    }

    private boolean isLevelDecreasing(Token token) {

        return token.getLexeme().endsWith(CLOSING_CURLY_BRACKET);
    }
}
