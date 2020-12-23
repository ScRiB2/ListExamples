package com.scrib.tasks;

import com.scrib.classes.Box;
import com.scrib.utils.ArrayListUtils;
import com.scrib.utils.FileWorker;

import java.io.IOException;
import java.util.ArrayList;

public class SecondTaskWorker {

    public static ArrayList<String> solve(ArrayList<String> lines) {
        try {
            if (lines.size() == 0) {
                throw new IndexOutOfBoundsException();
            }

            ArrayList<Box> boxes = new ArrayList<>();

            for (String line : lines) {
                ArrayList<Integer> tempLine = ArrayListUtils.getNumbersFromLine(line, " ");

                if (tempLine.size() != 3) {
                    throw new IndexOutOfBoundsException();
                }
                for (Integer number : tempLine) {
                    if (number <= 0) {
                        throw new IndexOutOfBoundsException();
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
                ArrayList<String> resultStrings = new ArrayList<>();
                resultStrings.add(resultBox.toString());
                return resultStrings;
            }

            ArrayList<Box> result = finalSort(sortedByVolume);
            ArrayList<String> resultStrings = new ArrayList<>();

            for (Box box : result) {
                resultStrings.add(box.toString());
            }
            return resultStrings;
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }


    public static void run(String[] args) {
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

            ArrayList<String> result = solve(lines);


            StringBuilder listString = new StringBuilder();
            for (String str : result) {
                listString.append(str).append("\n");
            }

            FileWorker.writeLineToFile(outputPath, listString.toString());

        } catch (NumberFormatException e) {
            System.out.println("Error in input data");
        } catch (IOException e) {
            System.out.println("Error reading input file");
        }
    }

    static ArrayList<Box> finalSort(ArrayList<Box> array) {
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
