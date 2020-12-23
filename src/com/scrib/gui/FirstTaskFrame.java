package com.scrib.gui;

import com.scrib.tasks.FirstTaskWorker;
import com.scrib.utils.ArrayListUtils;
import com.scrib.utils.FileWorker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static java.awt.Component.CENTER_ALIGNMENT;

public class FirstTaskFrame {
    JFrame jfrm;
    String resultString;
    JTable jTabData;
    JTable jTabResult;
    DefaultTableModel tableModelData;
    DefaultTableModel tableModelResult;
    JScrollPane jscrlpData;
    JScrollPane jscrlpResult;

    JPanel panelData;
    JPanel panelResult;

    public FirstTaskFrame() {
        jfrm = new JFrame("FirstTask");
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
                try {
                    String data = FileWorker.readLineFromFile(file.getPath());
                    tableModelData.setColumnCount(0);
                    tableModelResult.setColumnCount(0);
                    panelResult.setVisible(false);
                    if (data.length() > 0) {
                        ArrayList<Integer> numbers = ArrayListUtils.getNumbersFromLine(data, ",");
                        for (Integer number: numbers) {
                            tableModelData.addColumn("", new Object[]{number});
                        }
                        for (int i = 0; i < jTabData.getColumnCount(); i++) {
                            TableColumn column = jTabData.getColumnModel().getColumn(i);
                            column.setPreferredWidth(30);
                        }

                        jfrm.getContentPane().add(panelData);
                        jfrm.invalidate();
                        jfrm.validate();
                    }
                    resultString = FirstTaskWorker.solve(data);
                    if (resultString.length() > 0) {
                        ArrayList<Integer> numbers = ArrayListUtils.getNumbersFromLine(resultString, ",");
                        for (Integer number: numbers) {
                            tableModelResult.addColumn("", new Object[]{number});
                        }
                        for (int i = 0; i < tableModelResult.getColumnCount(); i++) {
                            TableColumn column = jTabResult.getColumnModel().getColumn(i);
                            column.setPreferredWidth(30);
                        }

                        panelResult.setVisible(true);

                        jfrm.getContentPane().add(panelResult);
                        jfrm.invalidate();
                        jfrm.validate();
                    }
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


        final JLabel labelData = new JLabel("Data: ");
        labelData.setAlignmentX(CENTER_ALIGNMENT);
        labelData.setAlignmentY(CENTER_ALIGNMENT);

        tableModelData = new DefaultTableModel();


        jTabData = new JTable(tableModelData);
        jscrlpData = new JScrollPane(jTabData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jTabData.setPreferredScrollableViewportSize(new Dimension(250, 25));
        jTabData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jscrlpData.setAlignmentX(CENTER_ALIGNMENT);
        jscrlpData.setAlignmentY(CENTER_ALIGNMENT);

        panelData = new JPanel();
        panelData.add(labelData);
        panelData.add(jscrlpData);
        panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));

        final JLabel labelResult = new JLabel("Result: ");
        labelResult.setAlignmentX(CENTER_ALIGNMENT);
        labelResult.setAlignmentY(CENTER_ALIGNMENT);

        tableModelResult = new DefaultTableModel();

        jTabResult = new JTable(tableModelResult);
        jscrlpResult = new JScrollPane(jTabResult, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jTabResult.setPreferredScrollableViewportSize(new Dimension(250, 25));
        jTabResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jscrlpData.setAlignmentX(CENTER_ALIGNMENT);
        jscrlpData.setAlignmentY(CENTER_ALIGNMENT);

        panelResult = new JPanel();
        panelResult.add(labelResult);
        panelResult.add(jscrlpResult);
        panelResult.add(saveButton);
        panelResult.setLayout(new BoxLayout(panelResult, BoxLayout.Y_AXIS));


        panel.add(loadButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jfrm.getContentPane().add(panel);

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }
}
