package org.codeformatter.formatters.impl;

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
import org.codeformatter.formatters.FormatterStateTransitions;
import org.codeformatter.formatters.impl.externalrepresentations.FormatterTransitionOnToken;
import org.codeformatter.formatters.impl.externalrepresentations.FormatterTransitionsForState;
import org.codeformatter.tokens.Token;
import org.codeformatter.utils.YamlListConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

@Slf4j
public class ExternalizedFormatterStateTransitions implements FormatterStateTransitions {

    private final Yaml yaml;
    private final Map<Pair<String, String>, String> transitions;

    public ExternalizedFormatterStateTransitions(String pathToStateConfig) {
        this.yaml = new Yaml(new YamlListConstructor<>(FormatterTransitionsForState.class));
        this.transitions = new HashMap<>();

        initializeStateTransitions(pathToStateConfig);
    }

    private void initializeStateTransitions(String pathToStateConfig) {

        try (
                InputStream inputStream = new FileInputStream(pathToStateConfig)
        ) {

            List<FormatterTransitionsForState> formatterStateTransitions = yaml.load(inputStream);

            formatterStateTransitions.forEach(this::addStateTransitions);

        } catch (FileNotFoundException e) {
            log.error("Unable to find file ({}) with formatter state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException("Unable to find file with formatter state transitions", e);
        } catch (IOException e) {
            log.error("Exception while reading file ({}) with formatter state transition rules", pathToStateConfig);
            throw new ExternalizedConfigException(
                    "Exception while reading file with formatter state transition rules", e
            );
        } catch (YAMLException yamlException) {
            log.error("Unable to parse yaml file with formatter state transitions");
            throw new ExternalizedConfigException(
                    "Unable to parse yaml file with formatter state transitions",
                    yamlException
            );
        }
    }

    private void addStateTransitions(FormatterTransitionsForState formatterTransitionsForState) {

        String stateToTransitionFrom = formatterTransitionsForState.getState();

        for (FormatterTransitionOnToken transitionOnToken : formatterTransitionsForState.getTransitions()) {

            String tokenName = transitionOnToken.getTokenName();
            String stateToTransitionTo = transitionOnToken.getStateToTransferTo();

            transitions.put(Pair.of(stateToTransitionFrom, tokenName), stateToTransitionTo);
        }
    }


    @Override
    public FormatterState nextState(FormatterState state, Token token) {

        String formatterStateName = transitions.get(
                Pair.of(state.getState(), token.getName())
        );

        if (formatterStateName == null) {
            formatterStateName = transitions.get(Pair.of(state.getState(), null));
        }

        log.debug("[FORMATTER] For state: {} and token type: {} transition to state {}",
                  state.getState(), token.getName(), formatterStateName);
        return FormatterState.of(formatterStateName);
    }
}
