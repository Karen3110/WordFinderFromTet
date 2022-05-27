package com.company;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class WordFinder {

    private static final List<String> data = new LinkedList<>();
    /**
     * is map with lines which line is containing static field keyWord
     * *                     the values are represented with <lineNumber,lineText> pairs
     */
    private static final Map<Integer, String> linesWithKey;

    public static final String fileName = File.separator + Main.fileName;
    public static final String keyWord = Main.keyWord;


    static {
        readFromFile();
        linesWithKey = findLinesWithKey();
    }


    /**
     * @return map with lines, which line is containing keyWord
     * *                     the values are represented with <lineNumber,lineText> pairs
     */
    private static Map<Integer, String> findLinesWithKey() {
        Map<Integer, String> foundedLines = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toLowerCase(Locale.ROOT).contains(keyWord)) {
                foundedLines.put(i, data.get(i).trim());
            }
        }
        return foundedLines;
    }

    /**
     * methods reads file data line by line and adds it to data list with type of List<String>>
     */
    private static void readFromFile() {
        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
    }

    /***
     * method does filtration depended on @param isPart and @param isCaseSensitive
     *
     * @param isPart means that if @param keyWord is a part of another word
     * @param isCaseSensitive is flag determines that the search process will be completed taking into account case sensitivity or not
     * @return filtered data
     */

    // TODO may be there is a more compact way to do filtration, need to review it
    private static Map<Integer, String> doFiltration(final boolean isPart, final boolean isCaseSensitive) {
        Map<Integer, String> filteredData = new HashMap<>();
        for (Map.Entry<Integer, String> entry : linesWithKey.entrySet()) {
            // not part and case sensitive
            if (!isPart && isCaseSensitive) {
                if (Arrays.stream(entry.getValue().split(" ")).collect(Collectors.toList()).contains(keyWord)) {
                    filteredData.put(entry.getKey(), entry.getValue());
                }
            }
            // is part and is case sensitive
            else if (isPart && isCaseSensitive) {
                if (entry.getValue().contains(keyWord)) {
                    filteredData.put(entry.getKey(), entry.getValue());
                }
            } else if (isPart) {
                return linesWithKey;
            }
        }
        return filteredData;
    }


    /***
     * params are passed from terminal.
     * this overload reads and finds only keyWord from fileName.
     * the search process will work without "part" and with "case sensitivity"
     */
    public static void startWorking() {

        Map<Integer, String> filteredData = doFiltration(false, true);
        printer(filteredData, false, true);

    }

    /***
     * params are passed from terminal.
     * this overload reads and finds only keyWord from fileName.
     * the search process will work with "part" and with "case sensitivity"
     */
    public static void startWorking(boolean isPart) {
        Map<Integer, String> filteredData = doFiltration(true, true);
        printer(filteredData, true, true);

    }

    /***
     * params are passed from terminal.
     * this overload reads and finds only keyWord from fileName.
     * the search process will work with "part" and without "case sensitivity"
     */
    public static void startWorking(boolean isPart, boolean isCaseSensitive) {
        Map<Integer, String> filteredData = doFiltration(true, false);
        printer(filteredData, true, false);
    }


    private static void printer(Map<Integer, String> filteredData, boolean isPart, boolean isCaseSensitive) {
        for (Map.Entry<Integer, String> entry : filteredData.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "\t" + value);

            linePrinter(value, isPart, isCaseSensitive);

        }
    }

    // TODO may be there is a more compact way to print lines, need to refactor it
    private static void linePrinter(String text, boolean isPart, boolean isCaseSensitive) {
        System.out.print("\t");
        String[] s = text.split(" ");

        // not part and case sensitive
        if (!isPart && isCaseSensitive) {
            for (int j = 0; j < s.length; j++) {
                String item = s[j];
                if (item.equals(keyWord)) {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("^");
                    }
                } else {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("-");
                    }
                }
                if (j < s.length - 1) {
                    System.out.print("-");
                }
            }
        }
        // is part and is case sensitive
        else if (isPart && isCaseSensitive) {

            for (int j = 0; j < s.length; j++) {
                String item = s[j];
                if (item.contains(keyWord)) {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("^");
                    }
                } else {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("-");
                    }
                }
                if (j < s.length - 1) {
                    System.out.print("-");
                }
            }

        }
        // is part and not case sensitive
        else if (isPart) {

            for (int j = 0; j < s.length; j++) {
                String item = s[j];
                if (item.toLowerCase(Locale.ROOT).contains(keyWord)) {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("^");
                    }
                } else {
                    for (int i = 0; i < item.length(); i++) {
                        System.out.print("-");
                    }
                }
                if (j < s.length - 1) {
                    System.out.print("-");
                }
            }
        }

        System.out.println();
    }
}
