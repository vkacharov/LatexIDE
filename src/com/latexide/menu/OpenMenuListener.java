package com.latexide.menu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class OpenMenuListener implements ActionListener {
    private JPanel parent;
    private Consumer<String> callback;
    private JFileChooser fileChooser;

    public OpenMenuListener(JPanel parent, Consumer<String> callback) {
        this.parent = parent;
        this.callback = callback;

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Latex Files", "tex");
        fileChooser.setFileFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand());
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String code = Files.readString(Path.of(selectedFile.getAbsolutePath()));
                callback.accept(code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
