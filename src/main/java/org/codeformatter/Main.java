package org.codeformatter;

import org.codeformatter.codesources.CodeSource;
import org.codeformatter.codesources.CommandLineCodeSource;
import org.codeformatter.codesources.FileCodeSource;
import org.codeformatter.formatters.CodeFormatter;

public class Main {

    //To run formatting for testSourceCode.java add VM option: src\main\resources\testSourceCode.java
    public static void main(String[] args) {

        CodeSource codeSource = getCodeSource(args);

        CodeFormatter codeFormatter = new CodeFormatter();

        String formattedCode = codeFormatter.formatCode(codeSource.getCode());

        System.out.println(formattedCode);
    }

    private static CodeSource getCodeSource(String[] args) {

        return args.length == 0 ? new CommandLineCodeSource()
                                : new FileCodeSource(args[0]);
    }

}
