package com.scrib.utils;

import java.io.*;
import java.util.ArrayList;

public class FileWorker {
    public static String readLineFromFile(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        reader.close();
        return line;
    }

    public static ArrayList<String> readLinesFromFile(String path) throws IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }

    public static void writeLineToFile(String path, String line) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(line);
            writer.flush();
        } catch (IOException ex) {
            System.out.println("Error writing to file on path " + path);
        }
    }
}
