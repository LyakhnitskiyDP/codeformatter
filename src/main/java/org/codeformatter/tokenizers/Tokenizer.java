package org.codeformatter.tokenizers;

import java.util.Deque;
import java.util.Queue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.lexers.DefaultToken;
import org.codeformatter.lexers.Token;

@Slf4j
@RequiredArgsConstructor
public abstract class Tokenizer {

    private final Deque<Character> characterDeque;
    private boolean cycleIsCompleted;

    @Getter
    private final Queue<Token> completedTokens;

    @Setter
    private StringBuilder currentLexeme = new StringBuilder();

    abstract boolean patternIsFound();

    abstract boolean patternIsComplete();

    abstract String getLexemeName();


    public String getCurrentLexeme() {

        return currentLexeme.toString();
    }

    public void consumeCharacterFromQueue() {

        Character ch = characterDeque.removeFirst();

        currentLexeme.append(ch);

        if (!patternIsFound()) {
            passUnusedCharacters();
            completeCycle();
            clearCurrentLexeme();
        } else {
            log.debug("{} has consumed char: {}", this.getClass().getSimpleName(), ch);
            log.debug("Char queue: size = {} {}", characterDeque.size(), characterDeque);
        }

        if (patternIsComplete()) {
            appendCompletedToken();
            completeCycle();
            clearCurrentLexeme();
        }
    }

    public void consumeCharactersFromQueue() {

        while (characterDeque.size() > 0 && !cycleIsCompleted) {
            consumeCharacterFromQueue();
        }
        startNewCycle();
    }

    protected void appendCompletedToken() {

        completedTokens.add(
                new DefaultToken(getLexemeName(), getCurrentLexeme().trim())
        );
    }

    private void completeCycle() {

        cycleIsCompleted = true;
    }

    private void startNewCycle() {

        cycleIsCompleted = false;
    }

    protected void passUnusedCharacters() {

        for (int i = currentLexeme.toString().toCharArray().length - 1; i >= 0; i--) {
            char ch = currentLexeme.toString().toCharArray()[i];
            characterDeque.addFirst(ch);
            log.debug("{} has passed unused char: {}", this.getClass().getSimpleName(), ch);
            log.debug("Char queue: size = {} {}", characterDeque.size(), characterDeque);
        }

    }

    private void clearCurrentLexeme() {

        currentLexeme.delete(0, currentLexeme.length());
    }

}
