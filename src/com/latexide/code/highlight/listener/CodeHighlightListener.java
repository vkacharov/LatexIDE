package com.latexide.code.highlight.listener;

import com.latexide.code.highlight.CodeHighlight;
import com.latexide.code.highlight.CodeHighlightType;
import com.latexide.code.highlight.provider.CodeHighlightProvider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.List;

public class CodeHighlightListener implements DocumentListener {

    private CodeHighlightProvider highlightProvider;

    public CodeHighlightListener(CodeHighlightProvider highlightProvider) {
        this.highlightProvider = highlightProvider;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        highlightEvent(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        highlightEvent(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void highlightEvent(DocumentEvent e) {
        Runnable doHighlight = () -> {
            try {
                DefaultStyledDocument styledDoc = (DefaultStyledDocument) e.getDocument();

                SimpleAttributeSet normal = new SimpleAttributeSet();
                StyleConstants.setForeground(normal, Color.BLACK);
                StyleConstants.setItalic(normal, false);
                styledDoc.setCharacterAttributes(0, styledDoc.getLength(), normal, true);

                String code = styledDoc.getText(0, e.getDocument().getLength());

                List<CodeHighlight> highlights = highlightProvider.highlightCode(code);
                highlights.stream().forEach(highlight -> {
                    SimpleAttributeSet highlightStyle = resolveHighlightStyle(highlight.getType());
                    styledDoc.setCharacterAttributes(highlight.getOffset(), highlight.getLength(), highlightStyle, true);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    private SimpleAttributeSet resolveHighlightStyle(CodeHighlightType highlightType) {
        SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
        switch(highlightType) {
            case FUNCTION:
                StyleConstants.setForeground(highlightStyle, Color.RED);
                StyleConstants.setItalic(highlightStyle, true);
        }

        return highlightStyle;
    }
}
