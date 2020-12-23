package com.scrib.gui;

import com.scrib.tasks.ThirdTaskWorker;
import com.scrib.utils.FileWorker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static java.awt.Component.CENTER_ALIGNMENT;

public class ThirdTaskFrame {
    JFrame jfrm;
    String resultString;

    public ThirdTaskFrame() {
        jfrm = new JFrame("ThirdTask");
        jfrm.getContentPane().setLayout(new FlowLayout());
        jfrm.setSize(250, 120);

        JPanel panel = new JPanel();

        JButton saveButton = new JButton("Save result");
        saveButton.addActionListener(e -> {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Save");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                FileWorker.writeLineToFile(file.getPath(), resultString);
            }
        });

        JButton loadButton = new JButton("Load data");
        loadButton.addActionListener(e -> {
            if (panel.getComponentCount() > 1) {
                panel.remove(1);
                jfrm.invalidate();
                jfrm.validate();
            }
            JFileChooser fileOpen = new JFileChooser();

            fileOpen.setFileFilter(new FileFilter() {

                public String getDescription() {
                    return "TXT Files (*.txt)";
                }

                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        String filename = f.getName().toLowerCase();
                        return filename.endsWith(".txt");
                    }
                }
            });

            int ret = fileOpen.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                try {
                    ArrayList<String> data = FileWorker.readLinesFromFile(file.getPath());
                    resultString = ThirdTaskWorker.solve(data);
                    panel.add(saveButton);
                    jfrm.invalidate();
                    jfrm.validate();
                } catch (Exception ignore) {
                    JFrame error = new JFrame("Error");
                    error.getContentPane().setLayout(new FlowLayout());
                    error.setSize(200, 70);

                    final JLabel labelData1 = new JLabel("Error data in file");
                    labelData1.setAlignmentX(CENTER_ALIGNMENT);
                    labelData1.setAlignmentY(CENTER_ALIGNMENT);
                    error.getContentPane().add(labelData1);
                    error.setLocationRelativeTo(null);
                    error.setVisible(true);
                }
            }
        });

        panel.add(loadButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jfrm.getContentPane().add(panel);

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }
}
