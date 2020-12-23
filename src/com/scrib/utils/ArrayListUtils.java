package com.scrib.utils;


import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListUtils {

    public enum Order {ASC, DESC}

    public static ArrayList<Integer> getNumbersFromLine(String line, String delimiter) {
        String[] words = line.split(delimiter);
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String word : words) {
            Integer number = Integer.parseInt(word.trim());
            numbers.add(number);
        }
        return numbers;
    }

    public static <T> ArrayList<T> sortArrayList(ArrayList<T> array, Comparator<T> comparator, Order order, boolean isUniq) {
        if (array.size() == 0) {
            return new ArrayList<>();
        }

        int length = array.size();
        int i = 0;
        T max = array.get(i);

        for (T z : array) {
            if (comparator.compare(z, max) > 0) {
                max = z;
            }
        }

        ArrayList<T> sortedNumbers = new ArrayList<>();

        do {
            ArrayList<T> mins = new ArrayList<T>();
            mins.add(max);
            boolean isGetNumber = false;
            T prevMaxNumber = max;
            try {
                prevMaxNumber = sortedNumbers.get(sortedNumbers.size() - 1);
                isGetNumber = true;
            } catch (Exception ignored) {
            }
            for (T number : array) {
                if (isGetNumber) {
                    if (comparator.compare(prevMaxNumber, number) < 0 && comparator.compare(number, mins.get(0)) < 0) {
                        mins = new ArrayList<T>();
                        mins.add(number);
                    } else if (comparator.compare(number, mins.get(0)) == 0 && !number.equals(mins.get(0))) {
                        mins.add(number);
                    }
                } else {
                    if (comparator.compare(number, mins.get(0)) < 0) {
                        mins = new ArrayList<T>();
                        mins.add(number);
                    } else if (comparator.compare(number, mins.get(0)) == 0 && !number.equals(mins.get(0))) {
                        mins.add(number);
                    }
                }
            }

            if (isUniq) sortedNumbers.add(mins.get(0));
            else {
                for (T min : mins) {
                    boolean isAdd = true;
                    for (T sortedNumber : sortedNumbers) {
                        if (sortedNumber == min) {
                            isAdd = false;
                            break;
                        }
                    }
                    if (isAdd)
                        sortedNumbers.add(min);
                }
            }
            i++;
        } while (i < length);

        if (order == Order.DESC) {
            ArrayList<T> temp = new ArrayList<>();
            for (int j = sortedNumbers.size() - 1; j > -1; j--) {
                temp.add(sortedNumbers.get(j));
            }
            sortedNumbers = temp;
        }
        return sortedNumbers;
    }

    public static ArrayList<Integer> getMaxSequence(ArrayList<Integer> sortedArray) {
        ArrayList<Integer> sequence = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 1;
        int length = 1;
        int tempStartIndex = 0;
        int tempEndIndex = 1;
        for (int i = 1; i < sortedArray.size(); i++) {
            if (sortedArray.get(i) - sortedArray.get(i - 1) != 1) {
                int diff = tempEndIndex - tempStartIndex;
                if (diff > length) {
                    length = diff;
                    startIndex = tempStartIndex;
                    endIndex = tempEndIndex;
                }
                tempStartIndex = i;
            }
            tempEndIndex = i + 1;
        }
        for (int i = startIndex; i < endIndex; i++) {
            sequence.add(sortedArray.get(i));
        }
        return sequence;
    }
}
