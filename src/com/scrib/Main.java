package com.scrib;

import com.scrib.gui.FirstTaskFrame;
import com.scrib.gui.SecondTaskFrame;
import com.scrib.gui.ThirdTaskFrame;
import com.scrib.tasks.FirstTaskWorker;
import com.scrib.tasks.SecondTaskWorker;
import com.scrib.tasks.ThirdTaskWorker;

import javax.swing.*;
import java.awt.*;


class MainFrame {
    JFrame jfrm;

    public MainFrame() {
        jfrm = new JFrame("MainFrame");
        jfrm.getContentPane().setLayout(new FlowLayout());
        jfrm.setSize(270, 150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton firstTaskButton = new JButton("1 task");
        firstTaskButton.addActionListener(e -> new FirstTaskFrame());

        JButton secondTaskButton = new JButton("2 task");
        secondTaskButton.addActionListener(e -> new SecondTaskFrame());

        JButton threeTaskButton = new JButton("3 task");
        threeTaskButton.addActionListener(e -> new ThirdTaskFrame());

        JPanel panel = new JPanel();
        panel.add(firstTaskButton);
        panel.add(secondTaskButton);
        panel.add(threeTaskButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jfrm.getContentPane().add(panel);
        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }
}

public class Main {

    public static void main(String[] args) {
        boolean isGUI = false;
        for (String arg : args) {
            if (arg.equals("-w")) {
                isGUI = true;
                break;
            }
        }
        if (isGUI) {
            SwingUtilities.invokeLater(MainFrame::new);
        } else {
            String inputPath = "";
            String outputPath = "";
            int taskNumber = -1;
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-t")) {
                    boolean isValidTask = false;
                    if (i + 1 <= args.length - 1) {
                        String taskData = args[i + 1];
                        try {
                            int tempNumber = Integer.parseInt(taskData);
                            if (tempNumber > 0 && tempNumber < 4) {
                                isValidTask = true;
                                taskNumber = tempNumber;
                            }
                        } catch (NumberFormatException ignore) {

                        }
                    }
                    if (!isValidTask) {
                        System.out.println("Task number is incorrect");
                        return;
                    }
                }

                if (args[i].equals("-o")) {
                    boolean isValidInput = false;
                    if (i + 1 <= args.length - 1) {
                        outputPath = args[i + 1];
                        isValidInput = true;
                    }
                    if (!isValidInput) {
                        System.out.println("Input path is incorrect");
                        return;
                    }
                }

                if (args[i].equals("-i")) {
                    boolean isValidInput = false;
                    if (i + 1 <= args.length - 1) {
                        inputPath = args[i + 1];
                        isValidInput = true;
                    }
                    if (!isValidInput) {
                        System.out.println("Input path is incorrect");
                        return;
                    }
                }
            }

            if (taskNumber == -1){
                System.out.println("Task number not specified (-t)");
                return;
            }
            if (inputPath.isEmpty()) {
                System.out.println("Input path not specified (-i)");
                return;
            }
            if (outputPath.isEmpty()) {
                System.out.println("Output path not specified (-o)");
                return;
            }
            String[] paths = new String[2];
            paths[0] = inputPath;
            paths[1] = outputPath;
            switch (taskNumber) {
                case 1 -> {
                    FirstTaskWorker.run(paths);
                }
                case 2 -> {
                    SecondTaskWorker.run(paths);
                }
                case 3 -> {
                    ThirdTaskWorker.run(paths);
                }
            }
        }
    }
}
