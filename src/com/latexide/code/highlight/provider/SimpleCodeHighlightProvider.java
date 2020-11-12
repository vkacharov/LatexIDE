package com.latexide.code.highlight.provider;

import com.latexide.code.highlight.CodeHighlight;
import com.latexide.code.highlight.CodeHighlightType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleCodeHighlightProvider implements CodeHighlightProvider {
    private Pattern functionPattern = Pattern.compile("\\\\\\w+");

    @Override
    public List<CodeHighlight> highlightCode(String code) {
        Matcher m = functionPattern.matcher(code);
        List<CodeHighlight> result = new ArrayList<>();
        while (m.find()) {
            result.add(new CodeHighlight(m.start(), m.end() - m.start(), CodeHighlightType.FUNCTION));
        }
        return result;
    }
}
