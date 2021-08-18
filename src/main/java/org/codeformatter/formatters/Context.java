package org.codeformatter.formatters;

import lombok.Data;
import org.codeformatter.io.Writer;

@Data
public class Context {

    private Writer writer;

    private int blockLevel;

    public Context(Writer writer, int blockLevel) {
        this.writer = writer;

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
