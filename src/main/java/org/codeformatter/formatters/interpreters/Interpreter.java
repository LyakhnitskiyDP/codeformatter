package org.codeformatter.formatters.interpreters;

import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.codeformatter.formatters.Formatter;
import org.codeformatter.utils.StringUtil;

@RequiredArgsConstructor
public abstract class Interpreter {

    private final StringBuilder content = new StringBuilder();

    private final Queue<Character> characterQueue;
    private final Formatter.Context context;

    abstract boolean patternIsFound(char ch);

    abstract boolean patternIsComplete();

    protected String getContent() {
        return content.toString();
    }

    public void consumeCharacterFromQueue() {

        char ch = characterQueue.remove();

        content.append(ch);

        if (!patternIsFound(ch)) {
            passUnusedCharacters();
            clearContent();
        }

        if (patternIsComplete()) {
            appendFoundConstruct();
            clearContent();
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

        context.getResult()
               .append(StringUtil.trimAndIndent(content.toString(), context.getBlockLevel() * 4))
               .append(System.lineSeparator());

        if (isLevelIncreasing()) {
            context.increaseBlockLevel();
        }
    }

    private boolean isLevelIncreasing() {

        return content.toString().endsWith("{");
    }

    private boolean isLevelDecreasing() {

        return content.toString().endsWith("}");
    }

    private void clearContent() {

        content.delete(0, content.length());
    }

}
