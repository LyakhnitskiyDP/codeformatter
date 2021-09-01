package org.codeformatter.lexers.impl;

import org.codeformatter.collections.Pair;
import org.codeformatter.lexers.Lexer;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;
import org.codeformatter.lexers.LexerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultLexerCommandRepository implements LexerCommandRepository {

    private static final String defaultTokenName = "Char";


    private static final Map<LexerState, Map<Character, LexerCommand>> commandMaps;

    private static final Map<Character, LexerCommand> initialStateCommands;
    private static final Map<Character, LexerCommand> for1StateCommands;
    private static final Map<Character, LexerCommand> for2StateCommands;
    private static final Map<Character, LexerCommand> for3StateCommands;
    private static final Map<Character, LexerCommand> forStateCommands;

    private static final Map<LexerState, Function<Character, LexerCommand>> defaultCommands;

    static {

        defaultCommands = Map.of(
                LexerState.of(LexerState.INITIAL), (ch) -> (lexerContext) -> { lexerContext.appendLexeme(ch); lexerContext.setTokenName(defaultTokenName); },
                LexerState.of(LexerState.FOR), (ch) -> (lexerContext) -> { lexerContext.appendLexeme(ch); lexerContext.setTokenName("for"); },
                LexerState.of(LexerState.FOR_1), (ch) -> (lexerContext) -> { lexerContext.appendLexeme(ch); lexerContext.setTokenName("Char"); },
                LexerState.of(LexerState.FOR_2), (ch) -> (lexerContext) -> { lexerContext.appendLexeme(ch); lexerContext.setTokenName("Char"); },
                LexerState.of(LexerState.FOR_3), (ch) -> (lexerContext) -> { lexerContext.appendLexeme(ch); lexerContext.setTokenName("Char"); }
                );

        char specialChar = (char) 13;
        initialStateCommands = Map.of(
            ' ', (context) -> { context.appendLexeme(' '); context.setTokenName("Whitespace"); },
            ';', (context) -> { context.appendLexeme(';'); context.setTokenName("Semicolon"); },
            '{', (context) -> { context.appendLexeme('{'); context.setTokenName("Opening Curly Bracket"); },
            '}', (context) -> { context.appendLexeme('}'); context.setTokenName("Closing Curly Bracket"); },
            '/', (context) -> { context.appendLexeme('/'); context.setTokenName("Slash"); },
            '\n', (context) -> { context.appendLexeme('\n'); context.setTokenName("Line separator"); },
            specialChar, (context) -> { context.appendLexeme('\n'); context.setTokenName("Line separator"); },
            'f', (context) -> { context.appendLexeme('f'); context.setTokenName("for"); },
            '*', (context) -> { context.appendLexeme('*'); context.setTokenName("Star"); },
            '\"', (context) -> { context.appendLexeme('\"'); context.setTokenName("Quotes"); }
        );

        for1StateCommands = Map.of(
            'o', (context) -> { context.appendLexeme('o'); context.setTokenName("for"); },
                ';', (context) -> { context.appendLexemePostpone(';'); context.setTokenName("Char"); }
        );

        for2StateCommands = Map.of(
            'r', (context) -> { context.appendLexeme('r'); context.setTokenName("for"); },
            ';', (context) -> { context.appendLexemePostpone(';'); context.setTokenName("Char"); }
        );

        for3StateCommands = Map.of(
            ' ', (context) -> { context.appendLexeme(' '); context.setTokenName("for"); },
            ';', (context) -> { context.appendLexemePostpone(';'); context.setTokenName("Char"); }
        );

        forStateCommands = Map.of(
            ')', (context) -> { context.appendLexeme(')'); context.setTokenName("for"); }
        );

        commandMaps = Map.of(
                LexerState.of(LexerState.INITIAL), initialStateCommands,
                LexerState.of(LexerState.FOR_1), for1StateCommands,
                LexerState.of(LexerState.FOR_2), for2StateCommands,
                LexerState.of(LexerState.FOR_3), for3StateCommands,
                LexerState.of(LexerState.FOR), forStateCommands
        );
    }

    @Override
    public LexerCommand getCommand(LexerState lexerState, char ch) {


        return commandMaps.get(lexerState)
                .getOrDefault(ch, defaultCommands.get(lexerState).apply(ch));
    }

}
