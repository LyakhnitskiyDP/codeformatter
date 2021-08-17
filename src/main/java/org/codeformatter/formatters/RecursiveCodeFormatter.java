package org.codeformatter.formatters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class RecursiveCodeFormatter {

    public String formatCode(String codeToFormat) {

        StringBuilder formattedCodeBuilder = new StringBuilder();

        log.debug("Code to format: {}", codeToFormat);

        String[] topLevelStatements = splitStatements(codeToFormat);

        if (topLevelStatements.length == 1) {

            formattedCodeBuilder.append(formatStatement(codeToFormat));
        } else {

            formattedCodeBuilder.append(formatStatements(topLevelStatements));
        }

        return formattedCodeBuilder.toString().trim();
    }

    private String formatStatement(String statementToFormat) {

        if (singleLineStatement(statementToFormat)) {

            return statementToFormat;
        } else {

            return formatCodeBlock(statementToFormat);
        }

    }

    String formatCodeBlock(String codeBlock) {
        StringBuilder formattedStatementBuilder = new StringBuilder();

        String openingPart = getOpeningPartOfBlockStatement(codeBlock);
        appendNewLineToStringBuilder(formattedStatementBuilder, openingPart);

        String innerCodePart = getInnerPartOfBlockStatement(codeBlock);
        String formattedInnerPart = formatCode(innerCodePart).indent(4);
        formattedStatementBuilder.append(formattedInnerPart);

        String enclosingPart = getEnclosingPartOfClockStatement(codeBlock);
        appendNewLineToStringBuilder(formattedStatementBuilder, enclosingPart);

        return formattedStatementBuilder.toString();
    }

    void appendNewLineToStringBuilder(StringBuilder builder, String content) {
        builder.append(content);
        builder.append(System.lineSeparator());
    }

    String getOpeningPartOfBlockStatement(String statement) {

        return statement.substring(0, statement.indexOf("{") + 1);
    }

    String getInnerPartOfBlockStatement(String statement) {

        return statement.substring(
                statement.indexOf("{") + 1,
                statement.lastIndexOf("}")
        );
    }

    String getEnclosingPartOfClockStatement(String statement) {

        return statement.substring(statement.lastIndexOf("}"));
    }

    private String formatStatements(String[] statements) {
        StringBuilder formattedCodeBuilder = new StringBuilder();

        for (int i = 0; i < statements.length; i++) {
            String part = statements[i];
            formattedCodeBuilder.append(formatCode(part));

            if (i < statements.length - 1) {
                formattedCodeBuilder.append(System.lineSeparator());
            }
        }

        return formattedCodeBuilder.toString();
    }

    public boolean singleLineStatement(String part) {

        return !part.contains("{");
    }

    String[] splitStatements(String whole) {

        List<String> splitParts = new ArrayList<>();
        int currentLevel = 0;
        String[] chars = whole.split("");

        for (int i = 0, lastTakenIndex = 0; i < chars.length; i++) {

            String currentChar = chars[i];

            if (currentChar.equals("{")) {
                currentLevel++;
            }

            if (currentChar.equals("}")) {
                currentLevel--;
            }

            if (isTopLevelStatement(currentChar, currentLevel)) {

                String topLevelStatement = whole.substring(lastTakenIndex, i + 1);
                splitParts.add(topLevelStatement);

                lastTakenIndex = i + 1;
            }

        }

        splitParts = trimParts(splitParts);

        return splitParts.toArray(new String[0]);
    }

    private boolean isTopLevelStatement(String statement, int level) {

        boolean statementTerminated = statement.endsWith(";") || statement.endsWith("}");

        return statementTerminated && level == 0;
    }

    private List<String> trimParts(Collection<String> parts) {

        return parts.stream().map(String::trim).collect(Collectors.toList());
    }

}
