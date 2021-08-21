package org.codeformatter.lexers;

import java.util.List;
import org.codeformatter.io.Reader;

public interface Lexer {

    List<Lexeme> getLexemes(Reader reader);

}
