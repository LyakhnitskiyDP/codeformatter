package org.codeformatter.formatters;

import java.util.LinkedList;
import java.util.Queue;
import lombok.Data;
import org.codeformatter.formatters.interpreters.Interpreter;
import org.codeformatter.formatters.interpreters.SimpleStatementInterpreter;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;

public class Formatter {

    public void format(Reader reader, Writer writer) {

        Queue<Character> charsToInterpret = new LinkedList<>();
        StringBuilder result = new StringBuilder();

        Context context = new Context(result, 0);
        Interpreter simpleInterpreter =
                new SimpleStatementInterpreter(charsToInterpret, context);

        Interpreter forLoopInterpreter =
                new SimpleStatementInterpreter(charsToInterpret, context);

        while (reader.hasMoreChars()) {

            char ch = reader.readChar();

            charsToInterpret.add(ch);

            //forLoopInterpreter.consumeCharacterFromQueue();

            simpleInterpreter.consumeCharacterFromQueue();

        }

        for (char ch : result.toString().toCharArray()) {
            writer.writeChar(ch);
        }

    }

    @Data
    public static class Context {

        private StringBuilder result;

        private int blockLevel;

        public Context(StringBuilder result, int blockLevel) {
            this.result = result;

            if (blockLevel < 0) {
                throw new IllegalArgumentException("Block level cannot be negative");
            }

            this.blockLevel = blockLevel;
        }

        public void increaseBlockLevel() {
            blockLevel++;
        }

        public void decreaseBlockLevel() {

            if (blockLevel - 1 < 0) {
                throw new IllegalStateException("Block level cannot be negative");
            }

            blockLevel--;
        }

    }


}
