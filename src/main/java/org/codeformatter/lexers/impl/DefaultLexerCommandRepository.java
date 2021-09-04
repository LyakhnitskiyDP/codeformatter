package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.LexerState.FOR;
import static org.codeformatter.lexers.impl.LexerState.FOR_1;
import static org.codeformatter.lexers.impl.LexerState.FOR_2;
import static org.codeformatter.lexers.impl.LexerState.FOR_3;
import static org.codeformatter.lexers.impl.LexerState.INITIAL;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_END1;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_START1;
import static org.codeformatter.utils.LoggingUtil.printChar;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;
import org.codeformatter.lexers.impl.commands.WriteCarriageReturnCommand;
import org.codeformatter.lexers.impl.commands.WriteCharCommand;
import org.codeformatter.lexers.impl.commands.WriteClosingCurlyBracketCommand;
import org.codeformatter.lexers.impl.commands.WriteForLoopCommand;
import org.codeformatter.lexers.impl.commands.WriteLineSeparatorCommand;
import org.codeformatter.lexers.impl.commands.WriteMultilineCommentCommand;
import org.codeformatter.lexers.impl.commands.WriteOpeningCurlyBracketCommand;
import org.codeformatter.lexers.impl.commands.WriteQuotesCommand;
import org.codeformatter.lexers.impl.commands.WriteSemicolonCommand;
import org.codeformatter.lexers.impl.commands.WriteSlashCommand;
import org.codeformatter.lexers.impl.commands.WriteStarCommand;
import org.codeformatter.lexers.impl.commands.WriteWhiteSpaceCommand;


@Slf4j
public class DefaultLexerCommandRepository implements LexerCommandRepository {

    private Map<Pair<String, Character>, LexerCommand> commands;

    LexerCommand writeWhiteSpaceCommand = new WriteWhiteSpaceCommand();

    LexerCommand writeClosingCurlyBracketCommand = new WriteClosingCurlyBracketCommand();

    LexerCommand writeOpeningCurlyBracketCommand = new WriteOpeningCurlyBracketCommand();

    LexerCommand writeSemicolonCommand = new WriteSemicolonCommand();

    LexerCommand writeSlashCommand = new WriteSlashCommand();

    LexerCommand writeLineSeparatorCommand = new WriteLineSeparatorCommand();

    LexerCommand writeCarriageReturnCommand = new WriteCarriageReturnCommand();

    LexerCommand writeCharCommand = new WriteCharCommand();

    LexerCommand writeQuotesCommand = new WriteQuotesCommand();

    LexerCommand writeStarCommand = new WriteStarCommand();

    LexerCommand writeMultilineCommentCommand = new WriteMultilineCommentCommand();


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
                Pair.of(FOR, ')'), new WriteForLoopCommand(),
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
        log.debug("Getting lexer command for state: {} and char: {}", lexerState.getState(), printChar(ch));

        String lexerStateName = lexerState.getState();

        LexerCommand lexerCommandToReturn = commands.get(Pair.of(lexerStateName, ch));

        if (lexerCommandToReturn == null) {
            log.debug("No special lexer command found, returning default one");
            lexerCommandToReturn = commands.get(Pair.of(lexerStateName, null));
        }

        return lexerCommandToReturn;
    }

}
