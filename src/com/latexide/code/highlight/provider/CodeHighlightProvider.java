package com.latexide.code.highlight.provider;

import com.latexide.code.highlight.CodeHighlight;

import java.util.List;

public interface CodeHighlightProvider {
    List<CodeHighlight> highlightCode(String code);
}
