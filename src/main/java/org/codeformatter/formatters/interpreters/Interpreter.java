package org.codeformatter.formatters.interpreters;

import java.util.Queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.codeformatter.formatters.Context;
import org.codeformatter.utils.StringUtil;

@RequiredArgsConstructor
public abstract class Interpreter {
    
    private static final int NUMBER_OF_SPACES_PER_TAB = 4;

    private static final String OPENING_CURLY_BRACKET = "{";
    private static final String CLOSING_CURLY_BRACKET = "}";

    @Setter
    private StringBuilder content = new StringBuilder();

    private final Queue<Character> characterQueue;
    private final Context context;

    abstract boolean patternIsFound();

    abstract boolean patternIsComplete();

    public String getContent() {

        return content.toString();
    }

    public void consumeCharacterFromQueue() {

        Character ch = characterQueue.poll();

        if (ch == null) {
            return;
        }

        content.append(ch);

        if (!patternIsFound()) {
            passUnusedCharacters();
            clearContent();
        }

        if (patternIsComplete()) {
            appendFoundConstruct();
            clearContent();
        }

    }

    public void consumeCharactersFromQueue() {
        while (characterQueue.size() > 0) {
            consumeCharacterFromQueue();
        }
    }

    protected void passUnusedCharacters() {

        for (char ch : content.toString().toCharArray()) {
            characterQueue.offer(ch);
        }

    }

    protected void appendFoundConstruct() {

        if (isLevelDecreasing()) {
            context.decreaseBlockLevel();
        }

        String construct = formatCodeConstruct();

        if (isLevelIncreasing()) {
            context.increaseBlockLevel();
        }

        for (char ch : construct.toCharArray()) {
            context.getWriter().writeChar(ch);
        }
    }

    private String formatCodeConstruct() {

        StringBuilder construct = new StringBuilder();
        int numberOfPrecedingSpaces = context.getBlockLevel() * NUMBER_OF_SPACES_PER_TAB;

        construct.append(StringUtil.trimAndIndent(content.toString(), numberOfPrecedingSpaces))
                 .append(System.lineSeparator());

        return construct.toString();
    }

    private boolean isLevelIncreasing() {

        return content.toString().endsWith(OPENING_CURLY_BRACKET);
    }

    private boolean isLevelDecreasing() {

        return content.toString().endsWith(CLOSING_CURLY_BRACKET);
    }

    private void clearContent() {

        content.delete(0, content.length());
    }

}
