package org.codeformatter.formatters;

import java.util.LinkedList;
import java.util.Queue;
import org.codeformatter.formatters.interpreters.ForLoopInterpreter;
import org.codeformatter.formatters.interpreters.Interpreter;
import org.codeformatter.formatters.interpreters.MultilineCommentInterpreter;
import org.codeformatter.formatters.interpreters.SimpleStatementInterpreter;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;

public class ChainedFormatter implements Formatter {

    public void format(Reader reader, Writer writer) {

        Queue<Character> charsToInterpret = new LinkedList<>();

        Context context = new Context(writer, 0);
        Interpreter simpleInterpreter =
                new SimpleStatementInterpreter(charsToInterpret, context);

        Interpreter forLoopInterpreter =
                new ForLoopInterpreter(charsToInterpret, context);

        Interpreter multilineCommentInterpreter =
                new MultilineCommentInterpreter(charsToInterpret, context);

        while (reader.hasMoreChars()) {

            char ch = reader.readChar();
            charsToInterpret.add(ch);

            multilineCommentInterpreter.consumeCharacterFromQueue();

            forLoopInterpreter.consumeCharacterFromQueue();

            simpleInterpreter.consumeCharactersFromQueue();
        }

    }


}
