package org.codeformatter.lexers.impl;

import static org.codeformatter.lexers.impl.LexerState.FOR;
import static org.codeformatter.lexers.impl.LexerState.FOR_1;
import static org.codeformatter.lexers.impl.LexerState.FOR_2;
import static org.codeformatter.lexers.impl.LexerState.FOR_3;
import static org.codeformatter.lexers.impl.LexerState.INITIAL;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_END1;
import static org.codeformatter.lexers.impl.LexerState.MULTILINE_COMMENT_START1;
import static org.codeformatter.tokens.LexicalConstants.CHAR;
import static org.codeformatter.tokens.LexicalConstants.CLOSING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.FOR_LOOP;
import static org.codeformatter.tokens.LexicalConstants.LINE_SEPARATOR;
import static org.codeformatter.tokens.LexicalConstants.OPENING_CURLY_BRACKET;
import static org.codeformatter.tokens.LexicalConstants.QUOTES;
import static org.codeformatter.tokens.LexicalConstants.SEMICOLON;
import static org.codeformatter.tokens.LexicalConstants.SLASH;
import static org.codeformatter.tokens.LexicalConstants.STAR;
import static org.codeformatter.tokens.LexicalConstants.WHITE_SPACE;

import java.util.Map;
import java.util.function.Function;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;


public class DefaultLexerCommandRepository implements LexerCommandRepository {

    private static final String defaultTokenName = CHAR;

    private static final Map<LexerState, Map<Character, LexerCommand>> commandMaps;

    private static final Map<Character, LexerCommand> initialStateCommands;

    private static final Map<Character, LexerCommand> forStateCommands;
    private static final Map<Character, LexerCommand> for1StateCommands;
    private static final Map<Character, LexerCommand> for2StateCommands;
    private static final Map<Character, LexerCommand> for3StateCommands;

    private static final Map<Character, LexerCommand> multilineCommentCommands;
    private static final Map<Character, LexerCommand> multilineCommentStartCommands;
    private static final Map<Character, LexerCommand> multilineCommentEndCommands;

    private static final Map<LexerState, Function<Character, LexerCommand>> defaultCommands;

    static {

        defaultCommands = Map.of(
                LexerState.of(INITIAL), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(defaultTokenName);
                },
                LexerState.of(FOR), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(FOR_LOOP);
                },
                LexerState.of(FOR_1), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(CHAR);
                },
                LexerState.of(FOR_2), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(CHAR);
                },
                LexerState.of(FOR_3), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(CHAR);
                },
                LexerState.of(MULTILINE_COMMENT), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(MULTILINE_COMMENT);
                },
                LexerState.of(MULTILINE_COMMENT_END1), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(MULTILINE_COMMENT);
                },
                LexerState.of(MULTILINE_COMMENT_START1), (ch) -> (lexerContext) -> {
                    lexerContext.appendLexeme(ch);
                    lexerContext.setTokenName(MULTILINE_COMMENT);
                }
            );

        //CARRIAGE RETURN (CR) (U+000D)
        char carriageReturnChar = (char) 13;

        initialStateCommands = Map.of(
            ' ', (context) -> {
                context.appendLexeme(' ');
                context.setTokenName(WHITE_SPACE);
            },
            ';', (context) -> {
                context.appendLexeme(';');
                context.setTokenName(SEMICOLON);
            },
            '{', (context) -> {
                context.appendLexeme('{');
                context.setTokenName(OPENING_CURLY_BRACKET);
            },
            '}', (context) -> {
                context.appendLexeme('}');
                context.setTokenName(CLOSING_CURLY_BRACKET);
            },
            '/', (context) -> {
                context.appendLexeme('/');
                context.setTokenName(SLASH);
            },
            '\n', (context) -> {
                context.appendLexeme('\n');
                context.setTokenName(LINE_SEPARATOR);
            },
            carriageReturnChar, (context) -> {
                context.appendLexeme('\n');
                context.setTokenName(LINE_SEPARATOR);
            },
            'f', (context) -> {
                context.appendLexeme('f');
                context.setTokenName(FOR_LOOP);
            },
            '*', (context) -> {
                context.appendLexeme('*');
                context.setTokenName(STAR);
            },
            '\"', (context) -> {
                context.appendLexeme('\"');
                context.setTokenName(QUOTES);
            }
        );

        multilineCommentCommands = Map.of();
        multilineCommentEndCommands = Map.of();
        multilineCommentStartCommands = Map.of();

        for1StateCommands = Map.of(
            'o', (context) -> {
                context.appendLexeme('o');
                context.setTokenName(FOR_LOOP);
            },
            ';', (context) -> {
                context.appendLexemePostpone(';');
                context.setTokenName(CHAR);
            }
        );

        for2StateCommands = Map.of(
            'r', (context) -> {
                context.appendLexeme('r');
                context.setTokenName(FOR_LOOP);
            },
            ';', (context) -> {
                context.appendLexemePostpone(';');
                context.setTokenName(CHAR);
            }
        );

        for3StateCommands = Map.of(
            ' ', (context) -> {
                context.appendLexeme(' ');
                context.setTokenName(FOR_LOOP);
            },
            ';', (context) -> {
                context.appendLexemePostpone(';');
                context.setTokenName(CHAR);
            }
        );

        forStateCommands = Map.of(
            ')', (context) -> {
                context.appendLexeme(')');
                context.setTokenName(FOR_LOOP);
            }
        );

        commandMaps = Map.of(
                LexerState.of(INITIAL), initialStateCommands,
                LexerState.of(FOR_1), for1StateCommands,
                LexerState.of(FOR_2), for2StateCommands,
                LexerState.of(FOR_3), for3StateCommands,
                LexerState.of(FOR), forStateCommands,
                LexerState.of(MULTILINE_COMMENT), multilineCommentCommands,
                LexerState.of(MULTILINE_COMMENT_START1), multilineCommentStartCommands,
                LexerState.of(MULTILINE_COMMENT_END1), multilineCommentEndCommands
        );
    }

    @Override
    public LexerCommand getCommand(LexerState lexerState, char ch) {

        return commandMaps.get(lexerState)
                .getOrDefault(ch, defaultCommands.get(lexerState).apply(ch));
    }

}
