package org.codeformatter.lexers.impl;

import static org.codeformatter.utils.LoggingUtil.makeCharPrintable;
import static org.codeformatter.utils.StringUtil.getFirstCharOrNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.exceptions.ExternalizedConfigException;
import org.codeformatter.lexers.impl.externalrepresentations.LexerActionOnChar;
import org.codeformatter.lexers.impl.externalrepresentations.LexerActionsForState;
import org.codeformatter.utils.YamlListConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

@Slf4j
public class ExternalizedLexerStateTransitions implements org.codeformatter.lexers.LexerStateTransitions {

    private final Yaml yaml;
    private final Map<Pair<String, Character>, String> transitions;

    public ExternalizedLexerStateTransitions(String pathToStateConfig) {
        this.yaml = new Yaml(new YamlListConstructor<>(LexerActionsForState.class));
        this.transitions = new HashMap<>();

        initializeStateTransitions(pathToStateConfig);
    }

    private void initializeStateTransitions(String pathToStateConfig) {

        try (
                InputStream inputStream = new FileInputStream(pathToStateConfig)
        ) {

            List<LexerActionsForState> lexerActionsOnStates = yaml.load(inputStream);

            lexerActionsOnStates.forEach(this::addStateTransitions);

        } catch (FileNotFoundException e) {
            log.error("Unable to find file ({}) with lexer state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException("Unable to find file with lexer state transitions", e);
        } catch (IOException e) {
            log.error("Exception while reading file ({}) with lexer state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException("Exception while reading file with lexer state transition rules", e);
        } catch (YAMLException yamlException) {
            log.error("Unable to parse yaml file with lexer state transitions");
            throw new ExternalizedConfigException(
                    "Unable to parse yaml file with lexer state transitions",
                    yamlException
            );
        }
    }

    private void addStateTransitions(LexerActionsForState lexerActionsOnChar) {

        String stateToTransitionFrom = lexerActionsOnChar.getState();

        for (LexerActionOnChar actionOnChar : lexerActionsOnChar.getActions()) {

            Character ch = getFirstCharOrNull(actionOnChar.getCh());
            String stateToTransitionTo = actionOnChar.getStateToTransferTo();

            transitions.put(Pair.of(stateToTransitionFrom, ch), stateToTransitionTo);
        }
    }

    @Override
    public LexerState nextState(LexerState lexerState, char ch) {

        String lexerStateName = transitions.get(Pair.of(lexerState.getState(), ch));

        if (lexerStateName == null) {
            lexerStateName = transitions.get(Pair.of(lexerState.getState(), null));
        }

        log.debug("[LEXER] For state: {} and char: {} transition to: {}",
                lexerState.getState(), makeCharPrintable(ch), lexerStateName);
        return LexerState.of(lexerStateName);
    }

}
