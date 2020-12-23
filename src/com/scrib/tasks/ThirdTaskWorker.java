package com.scrib.tasks;

import com.scrib.utils.FileWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class ThirdTaskWorker {

    public static String solve(ArrayList<String> lines) throws DataFormatException {
        if (lines.size() == 0) {
            throw new DataFormatException("No input data");
        }

        StringBuilder listString = new StringBuilder();
        for (String str : lines) {
            listString.append(str).append('\n');
        }

        String stringPattern = "([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_\\.-]+@[a-zA-Z0-9_\\.-]+(\\.[a-zA-Z0-9_-]+)*";
        String text = listString.toString();

        Pattern pattern = Pattern.compile(stringPattern);

        Matcher matcher = pattern.matcher(text);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            String email = text.substring(matcher.start(), matcher.end());
            boolean isAdd = true;
            for (String resultEmail : result) {
                if (resultEmail.equals(email)) {
                    isAdd = false;
                    break;
                }
            }

            String[] emailsParts = email.split("@");
            String user = emailsParts[0];
            String domain = emailsParts[1];
            while (user.startsWith(".")) {
                user = user.substring(1);
            }

            while (domain.endsWith(".")) {
                domain = domain.substring(0, domain.length() - 1);
            }

            if (!user.isEmpty() && !domain.isEmpty()) {
                email = user + "@" + domain;
            } else isAdd = false;

            if (isAdd) result.add(email);
        }


        StringBuilder resultString = new StringBuilder();
        for (String email : result) {
            resultString.append(email).append("\n");
        }
        return resultString.toString();
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

            String resultString = solve(lines);

            FileWorker.writeLineToFile(outputPath, resultString);

        } catch (NumberFormatException e) {
            System.out.println("Error in input data");
        } catch (IOException e) {
            System.out.println("Error reading input file");
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
}
