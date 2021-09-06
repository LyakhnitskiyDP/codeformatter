package org.codeformatter.formatters.impl;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.exceptions.ExternalizedConfigException;
import org.codeformatter.formatters.FormatterCommand;
import org.codeformatter.formatters.FormatterCommandRepository;
import org.codeformatter.formatters.impl.external_representations.FormatterCommandOnToken;
import org.codeformatter.formatters.impl.external_representations.FormatterCommandsForState;
import org.codeformatter.tokens.Token;
import org.codeformatter.utils.YamlListConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExternalizedFormatterCommandRepository implements FormatterCommandRepository {

    private static final String COMMAND_PACKAGE = "org.codeformatter.formatters.impl.commands";

    private final Map<Pair<String, String>, FormatterCommand> commands;

    private final Yaml yaml;

    public ExternalizedFormatterCommandRepository(String pathToCommands) {
        this.yaml = new Yaml(new YamlListConstructor<>(FormatterCommandsForState.class));
        this.commands = new HashMap<>();

        initializeCommands(pathToCommands);
    }

    private void initializeCommands(String pathToCommands) {

        try (
                InputStream inputStream = new FileInputStream(pathToCommands)
        ) {

            List<FormatterCommandsForState> formatterCommandsForStates = yaml.load(inputStream);

            formatterCommandsForStates.forEach(this::addCommandForState);

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

    private void addCommandForState(FormatterCommandsForState formatterCommandsForState) {

        String currentState = formatterCommandsForState.getState();

        for (FormatterCommandOnToken formatterCommandOnToken : formatterCommandsForState.getCommands()) {

            String tokenName = formatterCommandOnToken.getTokenName();
            FormatterCommand formatterCommand = createCommand(formatterCommandOnToken.getCommandName());

            commands.put(Pair.of(currentState, tokenName), formatterCommand);
        }
    }

    private FormatterCommand createCommand(String commandName) {

        //TODO: improve name construction
        String fullCommandName = COMMAND_PACKAGE + "." + commandName;

        try {

            return (FormatterCommand) Class.forName(fullCommandName)
                    .getDeclaredConstructor()
                    .newInstance();

        } catch (InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e) {
            log.error("Unable to reflectively create command with name: {}", fullCommandName);
            throw new ExternalizedConfigException("Unable to reflectively create command", e);
        } catch (ClassNotFoundException e) {
            log.error("Command with name {} not found", fullCommandName);
            throw new ExternalizedConfigException("Command not found", e);
        }
    }

    @Override
    public FormatterCommand getCommand(FormatterState formatterState, Token token) {
        log.debug("Getting new formatter command for state: {} and token: {}",
                formatterState.getState(), token.getName());

        FormatterCommand commandToReturn = commands.get(
                Pair.of(formatterState.getState(), token.getName())
        );

        if (commandToReturn == null) {
            commandToReturn = commands.get(Pair.of(formatterState.getState(), null));
        }

        log.debug("Returning command: {}", commandToReturn.getClass().getSimpleName());
        return commandToReturn;
    }

}
