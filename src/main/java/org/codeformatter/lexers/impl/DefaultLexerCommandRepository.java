package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.LexerState.FOR;
import static org.codeformatter.lexers.impl.LexerState.FOR_1;
import static org.codeformatter.lexers.impl.LexerState.FOR_2;
import static org.codeformatter.lexers.impl.LexerState.FOR_3;
import static org.codeformatter.lexers.impl.LexerState.INITIAL;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_END1;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_START1;
import static org.codeformatter.tokens.LexicalConstants.CARRIAGE_RETURN;
import static org.codeformatter.tokens.LexicalConstants.CHAR;
import static org.codeformatter.tokens.LexicalConstants.CLOSING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.LINE_SEPARATOR;
import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.tokens.LexicalConstants.SEMICOLON;
import static org.codeformatter.tokens.LexicalConstants.SLASH;
import static org.codeformatter.tokens.LexicalConstants.STAR;
import static org.codeformatter.tokens.LexicalConstants.WHITE_SPACE;

import java.util.HashMap;
import java.util.Map;

import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;


public class DefaultLexerCommandRepository implements LexerCommandRepository {

    private static final String defaultTokenName = CHAR;

    private Map<Pair<String, Character>, LexerCommand> commands;

    LexerCommand writeWhiteSpaceCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(WHITE_SPACE);
    };

    LexerCommand writeClosingCurlyBracketCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(CLOSING_CURLY_BRACKET);
    };

    LexerCommand writeOpeningCurlyBracketCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(OPENING_CURLY_BRACKET);
    };

    LexerCommand writeSemicolonCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(SEMICOLON);
    };

    LexerCommand writeSlashCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(SLASH);
    };

    LexerCommand writeLineSeparatorCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(LINE_SEPARATOR);
    };

    LexerCommand writeCarriageReturnCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(CARRIAGE_RETURN);
    };

    LexerCommand writeCharCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(CHAR);
    };

    LexerCommand writeQuotesCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(QUOTES);
    };

    LexerCommand writeStarCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(STAR);
    };

    LexerCommand writeMultilineCommentCommand = (ch, ctx) -> {
        ctx.appendLexeme(ch);
        ctx.setTokenName(MULTILINE_COMMENT);
    };


    public DefaultLexerCommandRepository() {

        commands = new HashMap<>();

        commands.putAll(Map.of(
                Pair.of(INITIAL, null), writeCharCommand,
                Pair.of(INITIAL, ';'), writeSemicolonCommand,
                Pair.of(INITIAL, '{'), writeOpeningCurlyBracketCommand,
                Pair.of(INITIAL, '}'), writeClosingCurlyBracketCommand,
                Pair.of(INITIAL, ' '), writeWhiteSpaceCommand,
                Pair.of(INITIAL, '/'), writeSlashCommand,
                Pair.of(INITIAL, '*'), writeStarCommand,
                Pair.of(INITIAL, '"'), writeQuotesCommand,
                Pair.of(INITIAL, '\n'), writeLineSeparatorCommand,
                Pair.of(INITIAL, '\r'), writeCarriageReturnCommand
        ));

        commands.putAll(Map.of(
                Pair.of(FOR, null), writeCharCommand,
                Pair.of(FOR, ')'), writeCharCommand,
                Pair.of(FOR_1, null), writeCharCommand,
                Pair.of(FOR_1, ';'), writeQuotesCommand,
                Pair.of(FOR_2, null), writeCharCommand,
                Pair.of(FOR_2, ';'), writeQuotesCommand,
                Pair.of(FOR_3, null), writeCharCommand,
                Pair.of(FOR_3, ';'), writeQuotesCommand
        ));

        commands.putAll(Map.of(
                Pair.of(MULTILINE_COMMENT, null), writeMultilineCommentCommand,
                Pair.of(MULTILINE_COMMENT_END1, null), writeMultilineCommentCommand,
                Pair.of(MULTILINE_COMMENT_START1, null), writeMultilineCommentCommand
        ));
    }

    @Override
    public LexerCommand getCommand(LexerState lexerState, char ch) {

        String lexerStateName = lexerState.getState();

        LexerCommand lexerCommandToReturn = commands.get(Pair.of(lexerStateName, ch));

        if (lexerCommandToReturn == null) {
            lexerCommandToReturn = commands.get(Pair.of(lexerStateName, null));
        }

        return lexerCommandToReturn;
    }

}
