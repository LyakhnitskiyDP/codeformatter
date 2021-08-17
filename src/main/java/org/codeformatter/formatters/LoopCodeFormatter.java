package org.codeformatter.formatters;

@Deprecated
public class LoopCodeFormatter {

    public String formatCode(String codeToFormat) {

        StringBuilder formattedCodeBuilder = new StringBuilder();

        int blockLevel = 0;
        boolean isNewLine = true;
        for (String ch : codeToFormat.split("")) {

            formattedCodeBuilder.append(
                switch (ch) {
                    case ";" -> {
                        isNewLine = true;
                        yield ch + System.lineSeparator();
                    }

                    case "{" -> {
                        blockLevel++;
                        isNewLine = true;
                        yield ch + System.lineSeparator();
                    }

                    case "}" -> {
                        blockLevel--;
                        isNewLine = true;
                        yield  indent(ch, blockLevel * 4) + System.lineSeparator();
                    }

                    case " " -> isNewLine ? "" : " ";

                    default -> {
                        String charToReturn = isNewLine ? indent(ch, blockLevel * 4) : ch;
                        isNewLine = false;
                        yield charToReturn;
                    }
                }
            );
        }

        return formattedCodeBuilder.toString();
    }

    private static String indent(String str, int padding) {

        return " ".repeat(Math.max(0, padding)) + str;
    }


}

