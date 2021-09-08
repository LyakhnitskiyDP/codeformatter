package org.codeformatter.lexers.impl;

import static org.codeformatter.utils.LoggingUtil.makeCharPrintable;
import static org.codeformatter.utils.StringUtil.getFirstCharOrNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.exceptions.CommandNotFoundException;
import org.codeformatter.exceptions.ExternalizedConfigException;
import org.codeformatter.lexers.LexerCommand;
import org.codeformatter.lexers.LexerCommandRepository;
import org.codeformatter.lexers.impl.externalrepresentations.LexerCommandOnChar;
import org.codeformatter.lexers.impl.externalrepresentations.LexerCommandsForState;
import org.codeformatter.utils.YamlListConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

@Slf4j
public class ExternalizedLexerCommandRepository implements LexerCommandRepository {

    private static final String COMMAND_PACKAGE = "org.codeformatter.lexers.impl.commands";

    private final Map<Pair<String, Character>, LexerCommand> commands;

    private final Yaml yaml;

    public ExternalizedLexerCommandRepository(String pathToCommands) {
        this.yaml = new Yaml(new YamlListConstructor<>(LexerCommandsForState.class));
        this.commands = new HashMap<>();

        initializeCommands(pathToCommands);
    }

    private void initializeCommands(String pathToCommands) {

        try (
                InputStream inputStream = new FileInputStream(pathToCommands)
        ) {

            List<LexerCommandsForState> lexerCommandsForStates = yaml.load(inputStream);

            lexerCommandsForStates.forEach(this::addCommandForState);

        } catch (FileNotFoundException e) {
            log.error("Unable to find file ({}) with lexer commands", pathToCommands);
            throw new ExternalizedConfigException("Unable to find file with lexer commands", e);
        } catch (IOException e) {
            log.error("Exception while reading file ({}) with lexer commands", pathToCommands);
            throw new ExternalizedConfigException("Exception while reading file with lexer commands", e);
        } catch (YAMLException yamlException) {
            log.error("Unable to parse yaml file with lexer commands");
            throw new ExternalizedConfigException("Unable to parse yaml file with lexer commands", yamlException);
        }
    }

    private void addCommandForState(LexerCommandsForState lexerCommandsForState) {

        String currentState = lexerCommandsForState.getState();

        for (LexerCommandOnChar lexerCommandOnChar : lexerCommandsForState.getCommands()) {

            Character ch = getFirstCharOrNull(lexerCommandOnChar.getCh());
            LexerCommand lexerCommand = createCommand(lexerCommandOnChar.getCommandName());

            commands.put(Pair.of(currentState, ch), lexerCommand);
        }
    }

    private LexerCommand createCommand(String commandName) {

        String fullCommandName = COMMAND_PACKAGE + "." + commandName;

        try {

            return (LexerCommand) Class.forName(fullCommandName)
                                       .getDeclaredConstructor()
                                       .newInstance();

        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            log.error("Unable to reflectively create lexer command with name: {}", fullCommandName);
            throw new ExternalizedConfigException("Unable to reflectively create lexer command", e);
        } catch (ClassNotFoundException e) {
            log.error("Command with name {} not found", fullCommandName);
            throw new CommandNotFoundException(String.format("Command %s not found", fullCommandName), e);
        }

    }

    @Override
    public LexerCommand getCommand(LexerState lexerState, char ch) {

        String lexerStateName = lexerState.getState();

        LexerCommand lexerCommandToReturn = commands.get(Pair.of(lexerStateName, ch));

        if (lexerCommandToReturn == null) {
            lexerCommandToReturn = commands.get(Pair.of(lexerStateName, null));
        }

        log.debug("[LEXER] For state: {} and char: {} give command: {}",
                  lexerState.getState(), makeCharPrintable(ch), lexerCommandToReturn.getClass().getSimpleName());

        return lexerCommandToReturn;
    }
}
