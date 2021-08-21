package org.codeformatter.lexers.tokenizers;

import java.util.Deque;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.codeformatter.lexers.Lexeme;

@Slf4j
@RequiredArgsConstructor
public abstract class Tokenizer {

    private final Deque<Character> characterDeque;
    private boolean cycleIsCompleted;

    @Getter
    private final List<Lexeme> lexemes;

    @Setter
    private StringBuilder currentToken = new StringBuilder();

    abstract boolean patternIsFound();

    abstract boolean patternIsComplete();

    abstract void appendFoundLexeme();

    public String getCurrentToken() {

        return currentToken.toString();
    }

    public void consumeCharacterFromQueue() {

        Character ch = characterDeque.removeFirst();

        currentToken.append(ch);

        if (!patternIsFound()) {
            passUnusedCharacters();
            completeCycle();
            clearToken();
        } else {
            log.debug("{} has consumed char: {}", this.getClass().getSimpleName(), ch);
            log.debug("Char queue: size = {} {}", characterDeque.size(), characterDeque);
        }

        if (patternIsComplete()) {
            appendFoundLexeme();
            completeCycle();
            clearToken();
        }

    }

    public void consumeCharactersFromQueue() {

        while (characterDeque.size() > 0 && !cycleIsCompleted) {
            consumeCharacterFromQueue();
        }
        startNewCycle();
    }

    private void completeCycle() {

        cycleIsCompleted = true;
    }

    private void startNewCycle() {

        cycleIsCompleted = false;
    }

    protected void passUnusedCharacters() {

        for (int i = currentToken.toString().toCharArray().length - 1; i >= 0; i--) {
            char ch = currentToken.toString().toCharArray()[i];
            characterDeque.addFirst(ch);
            log.debug("{} has passed unused char: {}", this.getClass().getSimpleName(), ch);
            log.debug("Char queue: size = {} {}", characterDeque.size(), characterDeque);
        }

    }

    private void clearToken() {

        currentToken.delete(0, currentToken.length());
    }

}
