package com.latexide.menu.listener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class SaveMenuListener implements ActionListener {
    private JTabbedPane tabbedPane;
    private JFileChooser fileChooser;

    public SaveMenuListener(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Latex Files", "tex");
        fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = fileChooser.showSaveDialog(tabbedPane);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            String code = getSelectedTabCode();

            try {
                if (fileToSave.exists()) {
                    if (showConfirmationDialog()) {
                        saveCodeToFile(code, fileToSave);
                    }
                } else {
                    fileToSave.createNewFile();
                    saveCodeToFile(code, fileToSave);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void saveCodeToFile(String code, File file) throws IOException{
        Files.writeString(Path.of(file.getAbsolutePath()), code);
        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
    }

    private boolean showConfirmationDialog() {
        int result = JOptionPane.showConfirmDialog(tabbedPane,"The file already exists. Override content?", "Save File",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private String getSelectedTabCode() {
        JPanel selectedPanel = (JPanel) tabbedPane.getSelectedComponent();
        JTextPane selectedTextPane = Stream.of(selectedPanel.getComponents())
                .filter(t -> t instanceof JTextPane)
                .map(t -> (JTextPane) t)
                .findFirst()
                .get();
        String code = selectedTextPane.getText();
        return code;
    }
}
