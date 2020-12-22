package com.scrib;


import com.scrib.classes.Box;
import com.scrib.utils.ArrayListUtils;
import com.scrib.utils.FileWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;


public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("File paths not entered");
            return;
        }
        String inputPath = args[0];
        String outputPath = args[1];

        try {
            ArrayList<String> lines = FileWorker.readLinesFromFile(inputPath);

            if (lines.size() == 0) {
                System.out.println("No input data");
                return;
            }

            ArrayList<Box> boxes = new ArrayList<>();

            for (String line : lines) {
                ArrayList<Integer> tempLine = ArrayListUtils.getNumbersFromLine(line, " ");

                if (tempLine.size() != 3) {
                    System.out.println("Error in input data");
                    return;
                }
                for (Integer number : tempLine) {
                    if (number <= 0) {
                        System.out.println("Error in input data");
                        return;
                    }
                }
                boxes.add(new Box(tempLine.get(0), tempLine.get(1), tempLine.get(2)));
            }

            ArrayList<Box> sortedByVolume = ArrayListUtils.sortArrayList(
                    boxes,
                    ((o1, o2) -> {
                        if (o1.getVolume() > o2.getVolume()) return 1;
                        else if (o1.getVolume() < o2.getVolume()) return -1;
                        return 0;
                    }),
                    ArrayListUtils.Order.ASC, false
            );

            if (sortedByVolume.size() == 1) {
                Box resultBox = sortedByVolume.get(0);
                FileWorker.writeLineToFile(outputPath, resultBox.toString());
            }

            ArrayList<Box> result = finalSort(sortedByVolume);


            StringBuilder listString = new StringBuilder();
            for (Box box : result) {
                listString.append(box.toString()).append("\n");
            }

            FileWorker.writeLineToFile(outputPath, listString.toString());

        } catch (NumberFormatException e) {
            System.out.println("Error in input data");
        } catch (IOException e) {
            System.out.println("Error reading input file");
        }
    }

    public static ArrayList<Box> finalSort(ArrayList<Box> array) {
        ArrayList<Box> result = new ArrayList<>();


        for (int i = 1; i < array.size(); i++) {
            Box prev = array.get(i - 1);
            Box curr = array.get(i);
            if (prev.getVolume() != curr.getVolume()) {
                result.add(array.get(i - 1));
            } else {
                int compareResult = prev.compareBy(Box.Selectors.BIG_SIDE, ArrayListUtils.Order.ASC, curr);
                if (compareResult == 0) {
                    compareResult = prev.compareBy(Box.Selectors.SMALLER_SIDE, ArrayListUtils.Order.DESC, curr);
                }
                if (compareResult == -1) {
                    result.add(prev);
                    result.add(curr);
                } else {
                    result.add(curr);
                    result.add(prev);
                }
                i++;
            }
        }

        if (result.size() != array.size()) result.add(array.get(array.size() - 1));
        return result;
    }
}
