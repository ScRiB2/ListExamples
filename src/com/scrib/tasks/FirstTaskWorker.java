package com.scrib.tasks;

import com.scrib.utils.ArrayListUtils;
import com.scrib.utils.FileWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class FirstTaskWorker {

    public static String solve(String line) throws DataFormatException {
        if (line == null) {
            throw new DataFormatException("No input data");
        }

        ArrayList<Integer> numbers = ArrayListUtils.getNumbersFromLine(line, ",");

        ArrayList<Integer> sortedNumbers = ArrayListUtils.sortArrayList(numbers, Integer::compareTo, ArrayListUtils.Order.ASC, true);

        ArrayList<Integer> sequence = ArrayListUtils.getMaxSequence(sortedNumbers);

        StringBuilder listString = new StringBuilder();
        for (Integer s : sequence) {
            listString.append(s).append(", ");
        }
        return listString.substring(0, listString.length() - 2);
    }

    public static void run(String[] args) {
        if (args.length < 2) {
            System.out.println("File paths not entered");
            return;
        }
        String inputPath = args[0];
        String outputPath = args[1];
        try {
            String line = FileWorker.readLineFromFile(inputPath);

            if (line == null) {
                System.out.println("No input data");
                return;
            }

            String result = solve(line);

            FileWorker.writeLineToFile(outputPath, result.toString());
        } catch (NumberFormatException e) {
            System.out.println("Error in input data");
        } catch (IOException e) {
            System.out.println("Error reading input file");
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
}
