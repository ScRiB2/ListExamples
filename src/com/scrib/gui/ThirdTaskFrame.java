package com.scrib.gui;

import com.scrib.tasks.ThirdTaskWorker;
import com.scrib.utils.FileWorker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static java.awt.Component.CENTER_ALIGNMENT;

public class ThirdTaskFrame {
    JFrame jfrm;
    String resultString;
    DefaultTableModel tableModelResult;
    JTable jTabResult;
    JScrollPane jscrlpResult;
    JPanel panelResult;

    public ThirdTaskFrame() {
        jfrm = new JFrame("ThirdTask");
        jfrm.getContentPane().setLayout(new FlowLayout());
        jfrm.setSize(350, 250);

        JPanel panel = new JPanel();

        JButton saveButton = new JButton("Save result");
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        saveButton.setAlignmentY(CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Save");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                FileWorker.writeLineToFile(file.getPath(), resultString);
            }
        });

        JButton loadButton = new JButton("Load data");
        loadButton.setAlignmentX(CENTER_ALIGNMENT);
        loadButton.setAlignmentY(CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> {
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
                tableModelResult.setRowCount(0);
                panelResult.setVisible(false);
                try {
                    ArrayList<String> data = FileWorker.readLinesFromFile(file.getPath());
                    resultString = ThirdTaskWorker.solve(data);
                    String[] resultList = resultString.split("\n");
                    for(String result: resultList){
                        tableModelResult.addRow(new Object[]{result});
                    }
                    panelResult.setVisible(true);
                    jfrm.getContentPane().add(panelResult);
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

        tableModelResult = new DefaultTableModel();

        jTabResult = new JTable(tableModelResult);
        tableModelResult.addColumn("emails");

        jscrlpResult = new JScrollPane(jTabResult);
        jscrlpResult.setAlignmentX(CENTER_ALIGNMENT);
        jscrlpResult.setAlignmentY(CENTER_ALIGNMENT);

        jTabResult.setPreferredScrollableViewportSize(new Dimension(250, 100));
        jTabResult.setEnabled(false);

        panel.add(loadButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jfrm.getContentPane().add(panel);

        panelResult = new JPanel();
        panelResult.add(jscrlpResult);
        panelResult.add(saveButton);
        panelResult.setLayout(new BoxLayout(panelResult, BoxLayout.Y_AXIS));

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }
}
