package org.codeformatter.formatters;

import lombok.Getter;

public class Context {

    @Getter
    private int blockLevel = 0;

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
