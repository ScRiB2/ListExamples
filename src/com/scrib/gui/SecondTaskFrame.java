package com.scrib.gui;

import com.scrib.tasks.SecondTaskWorker;
import com.scrib.utils.ArrayListUtils;
import com.scrib.utils.FileWorker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static java.awt.Component.CENTER_ALIGNMENT;

public class SecondTaskFrame {

    JTable jTabData;
    JTable jTabResult;
    DefaultTableModel tableModelData;
    DefaultTableModel tableModelResult;
    JScrollPane jscrlpData;
    JScrollPane jscrlpResult;
    JFrame jfrm;
    JPanel resultPanel;
    ArrayList<String> resultStrings;


    public SecondTaskFrame() {
        jfrm = new JFrame("TaskTwoFrame");
        jfrm.getContentPane().setLayout(new FlowLayout());
        jfrm.setSize(300, 500);

        final JLabel labelData = new JLabel("Data: ");
        labelData.setAlignmentX(CENTER_ALIGNMENT);
        labelData.setAlignmentY(CENTER_ALIGNMENT);

        tableModelData = new DefaultTableModel();


        jTabData = new JTable(tableModelData);
        tableModelData.addColumn("L");
        tableModelData.addColumn("W");
        tableModelData.addColumn("H");

        jscrlpData = new JScrollPane(jTabData);

        jTabData.setPreferredScrollableViewportSize(new Dimension(250, 100));


        jscrlpData.setAlignmentX(CENTER_ALIGNMENT);
        jscrlpData.setAlignmentY(CENTER_ALIGNMENT);

        JPanel panelData = new JPanel();
        panelData.add(labelData);
        panelData.add(jscrlpData);
        panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
        jfrm.getContentPane().add(panelData);


        JButton buttonSecondTask = new JButton("Get solution");
        ActionListener secondTaskListener = new SecondTaskListener();
        buttonSecondTask.addActionListener(secondTaskListener);

        JButton buttonAddRow = new JButton("Add row");
        ActionListener addRowListener = new AddRowListener();
        buttonAddRow.addActionListener(addRowListener);

        JButton buttonRemoveRow = new JButton("Delete row");
        ActionListener removeRowListener = new RemoveRowListener();
        buttonRemoveRow.addActionListener(removeRowListener);

        JPanel panel1 = new JPanel();
        panel1.add(buttonSecondTask);

        JButton saveButton = new JButton("Save result");
        saveButton.addActionListener(e -> {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Save");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                StringBuilder listString = new StringBuilder();
                for (String line : resultStrings) {
                    listString.append(line).append("\n");
                }

                FileWorker.writeLineToFile(file.getPath(), listString.toString());
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
                    ArrayList<String> data = FileWorker.readLinesFromFile(file.getPath());
                    ArrayList<ArrayList<Integer>> resultArray = new ArrayList<>();
                    for (String str : data) {
                        ArrayList<Integer> tempLine = ArrayListUtils.getNumbersFromLine(str, " ");
                        resultArray.add(tempLine);
                    }

                    while (tableModelData.getRowCount() > 0) {
                        tableModelData.removeRow(0);
                    }

                    for (ArrayList<Integer> integers : resultArray) {
                        tableModelData.addRow(new Object[]{integers.get(0), integers.get(1), integers.get(2)});
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

        JPanel panelLoad = new JPanel();
        panelLoad.add(loadButton);
        jfrm.getContentPane().add(panelLoad);

        JPanel panel2 = new JPanel();
        panel2.add(buttonAddRow);
        panel2.add(buttonRemoveRow);


        jfrm.getContentPane().add(panel2);
        jfrm.getContentPane().add(panel1);


        tableModelResult = new DefaultTableModel();


        jTabResult = new JTable(tableModelResult);
        tableModelResult.addColumn("L");
        tableModelResult.addColumn("W");
        tableModelResult.addColumn("H");

        jscrlpResult = new JScrollPane(jTabResult);

        jTabResult.setPreferredScrollableViewportSize(new Dimension(250, 100));


        final JLabel labelResult = new JLabel("Result: ");
        labelResult.setAlignmentX(CENTER_ALIGNMENT);
        labelResult.setAlignmentY(CENTER_ALIGNMENT);
        saveButton.setAlignmentX(CENTER_ALIGNMENT);
        saveButton.setAlignmentY(CENTER_ALIGNMENT);
        resultPanel = new JPanel();
        resultPanel.add(labelResult);
        resultPanel.add(jscrlpResult);
        resultPanel.add(saveButton);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        jTabResult.setEnabled(false);
        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);

    }

    class SecondTaskListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            while (tableModelResult.getRowCount() > 0) {
                tableModelResult.removeRow(0);
            }
            int columnCount = jTabData.getColumnCount();
            int rowCount = jTabData.getRowCount();
            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                StringBuilder listString = new StringBuilder();
                for (int j = 0; j < columnCount; j++) {
                    listString.append(jTabData.getValueAt(i, j).toString()).append(" ");
                }
                lines.add(listString.toString());
            }

            try {
                resultStrings = SecondTaskWorker.solve(lines);
            } catch (IndexOutOfBoundsException ignore) {
            }

            if (resultStrings.size() == 0) {
                resultPanel.setVisible(false);
                return;
            }

            resultPanel.setVisible(true);

            for (String str : resultStrings) {
                String[] splitStr = str.split(" ");
                tableModelResult.addRow(new Object[]{splitStr[0], splitStr[1], splitStr[2]});
            }
            jfrm.getContentPane().add(resultPanel);

            jfrm.invalidate();
            jfrm.validate();
        }
    }

    class AddRowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            tableModelData.addRow(new Object[]{"", "", ""});
        }
    }

    class RemoveRowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            tableModelData = (DefaultTableModel) jTabData.getModel();
            int indexRow = jTabData.getSelectedRow();
            if (indexRow >= 0)
                tableModelData.removeRow(indexRow);
        }
    }
}
