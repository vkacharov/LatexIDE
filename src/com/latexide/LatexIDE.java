package com.latexide;

import com.latexide.code.highlight.listener.CodeHighlightListener;
import com.latexide.code.highlight.provider.CodeHighlightProvider;
import com.latexide.code.highlight.provider.SimpleCodeHighlightProvider;
import com.latexide.menu.OpenMenuListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyEvent;
import java.io.File;

public class LatexIDE {
    private JPanel mainPanel;
    private JTextPane codePane;
    private JMenuBar menuBar;

    public LatexIDE() {
        CodeHighlightProvider highlightProvider = new SimpleCodeHighlightProvider();
        codePane.getDocument().addDocumentListener(new CodeHighlightListener(highlightProvider));
        menuBar = createMenuBar();
    }

    public JMenuBar createMenuBar() {
        menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.addActionListener(new OpenMenuListener(mainPanel, code -> {
            codePane.setText(code);
        }));
        file.add(open);
        menuBar.add(file);

        return menuBar;
    }
    public static void main(String[] args) {
        JFrame app = new JFrame("LatexIDE");
        LatexIDE latexIDE = new LatexIDE();
        app.setContentPane(latexIDE.mainPanel);
        app.setJMenuBar(latexIDE.menuBar);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.pack();
        app.setVisible(true);
    }
}
