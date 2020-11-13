package com.latexide;

import com.latexide.code.highlight.listener.CodeHighlightListener;
import com.latexide.code.highlight.provider.CodeHighlightProvider;
import com.latexide.code.highlight.provider.SimpleCodeHighlightProvider;
import com.latexide.menu.listener.OpenMenuListener;
import com.latexide.menu.listener.SaveMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LatexIDE {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JMenuBar menuBar;

    private CodeHighlightListener codeHighlightListener;

    public LatexIDE() {
        CodeHighlightProvider highlightProvider = new SimpleCodeHighlightProvider();
        codeHighlightListener = new CodeHighlightListener(highlightProvider);
        menuBar = createMenuBar();

        addCodeTab("untitled", "");
    }

    public JMenuBar createMenuBar() {
        menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.addActionListener(new OpenMenuListener(mainPanel, code -> {
            addCodeTab(code.getName(), code.getContent());
        }));
        file.add(open);

        JMenuItem save = new JMenuItem("Save", KeyEvent.VK_S);
        save.addActionListener(new SaveMenuListener(tabbedPane));
        menuBar.add(file);
        file.add(save);

        return menuBar;
    }

    private void addCodeTab(String title, String code) {
        JPanel newTabPanel = new JPanel();
        JTextPane newTabCodePane = new JTextPane();
        newTabCodePane.getDocument().addDocumentListener(codeHighlightListener);
        newTabCodePane.setText(code);
        newTabCodePane.setPreferredSize(new Dimension(500, 750));
        newTabPanel.add(newTabCodePane);

        tabbedPane.addTab(title, newTabPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
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
