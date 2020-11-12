package com.latexide.code.highlight;

public class CodeHighlight {
    private int offset;
    private int length;
    private CodeHighlightType type;

    public CodeHighlight(int offset, int length, CodeHighlightType type) {
        this.offset = offset;
        this.length = length;
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public CodeHighlightType getType() {
        return type;
    }
}
