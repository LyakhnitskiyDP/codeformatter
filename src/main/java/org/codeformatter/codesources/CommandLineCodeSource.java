package org.codeformatter.codesources;

import java.util.Scanner;

public class CommandLineCodeSource implements CodeSource {

    @Override
    public String getCode() {

        String line;
        try (Scanner scanner = new Scanner(System.in)) {

            line = scanner.nextLine();
        }

        return line;
    }

}
