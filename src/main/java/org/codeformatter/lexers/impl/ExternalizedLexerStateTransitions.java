package org.codeformatter.lexers.impl;

import lombok.extern.slf4j.Slf4j;
import org.codeformatter.collections.Pair;
import org.codeformatter.exceptions.ExternalizedConfigException;
import org.codeformatter.lexers.impl.external_representations.LexerTransitionOnChar;
import org.codeformatter.lexers.impl.external_representations.LexerTransitionsForState;
import org.codeformatter.utils.YamlListConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.codeformatter.utils.LoggingUtil.makeCharPrintable;

@Slf4j
public class ExternalizedLexerStateTransitions implements org.codeformatter.lexers.LexerStateTransitions {

    private final Yaml yaml;
    private final Map<Pair<String, Character>, String> transitions;

    public ExternalizedLexerStateTransitions(String pathToStateConfig) {
        this.yaml = new Yaml(new YamlListConstructor<>(LexerTransitionsForState.class));
        this.transitions = new HashMap<>();

        initializeStateTransitions(pathToStateConfig);
    }

    private void initializeStateTransitions(String pathToStateConfig) {

        try (
                InputStream inputStream = new FileInputStream(pathToStateConfig)
        ) {

            List<LexerTransitionsForState> lexerTransitionOnStates = yaml.load(inputStream);

            lexerTransitionOnStates.forEach(this::addStateTransitions);

        } catch (FileNotFoundException e) {
            log.error("Unable to find file ({}) with lexer state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException("Unable to find file with lexer state transitions", e);
        } catch (IOException e) {
            log.error("Exception while reading file ({}) with lexer state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException("Exception while reading file with lexer state transition rules", e);
        } catch (YAMLException yamlException) {
            log.error("Unable to parse yaml file with lexer state transitions");
            throw new ExternalizedConfigException("Unable to parse yaml file with lexer state transitions", yamlException);
        }
    }

    private void addStateTransitions(LexerTransitionsForState lexerTransitionsForState) {

        String stateToTransitionFrom = lexerTransitionsForState.getState();

        for (LexerTransitionOnChar transitionOnChar : lexerTransitionsForState.getTransitions()) {

            Character ch = getCharOrNull(transitionOnChar.getCh());
            String stateToTransitionTo = transitionOnChar.getStateToTransferTo();

            transitions.put(Pair.of(stateToTransitionFrom, ch), stateToTransitionTo);
        }
    }

    private Character getCharOrNull(String str) {

        return str == null ? null : str.charAt(0);
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
