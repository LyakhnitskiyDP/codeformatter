package org.codeformatter.formatters;

import java.util.LinkedList;
import java.util.Queue;
import lombok.Data;
import org.codeformatter.formatters.interpreters.IfStatementInterpreter;
import org.codeformatter.formatters.interpreters.Interpreter;
import org.codeformatter.formatters.interpreters.MultilineCommentInterpreter;
import org.codeformatter.formatters.interpreters.SimpleStatementInterpreter;
import org.codeformatter.io.Reader;
import org.codeformatter.io.Writer;

public class Formatter {

    public void format(Reader reader, Writer writer) {

        Queue<Character> charsToInterpret = new LinkedList<>();

        Context context = new Context(writer, 0);
        Interpreter simpleInterpreter =
                new SimpleStatementInterpreter(charsToInterpret, context);

        Interpreter forLoopInterpreter =
                new IfStatementInterpreter(charsToInterpret, context);

        MultilineCommentInterpreter commentInterpreter =
                new MultilineCommentInterpreter(charsToInterpret, context);

        while (reader.hasMoreChars()) {

            char ch = reader.readChar();
            charsToInterpret.add(ch);

            /*
            forLoopInterpreter.consumeCharacterFromQueue();

            commentInterpreter.consumeCharacterFromQueue();

             */

            simpleInterpreter.consumeCharactersFromQueue();

        }

    }


}
