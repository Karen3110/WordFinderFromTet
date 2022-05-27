package com.company;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordFinder {

    private final List<String> data;
    private final Map<Integer, String> linesWithKey;

    private final boolean isPart;
    private final boolean isSensitive;

    private static String fileName;
    private static String keyWord;


    private Function<String, Boolean> notPartAndSensitive = (value) -> value.equals(keyWord);
    private Function<String, Boolean> partAndSensitive = (value) -> value.contains(keyWord);
    private Function<String, Boolean> partAndNotSnesitive = (value) -> value.toLowerCase(Locale.ROOT).contains(keyWord);


    public WordFinder(final String keyWord, final String fileName) {
        WordFinder.keyWord = keyWord;
        WordFinder.fileName = File.separator + fileName;
        isPart = false;
        isSensitive = true;

        this.data = readFromFile();
        this.linesWithKey = findLinesWithKey();

    }

    public WordFinder(final String keyWord, final String fileName, final boolean partAndIsSensitive) {

        WordFinder.fileName = File.separator + fileName;
        WordFinder.keyWord = keyWord;
        if (partAndIsSensitive) {
            this.isPart = true;
            this.isSensitive = true;
        } else {
            this.isPart = true;
            this.isSensitive = false;
        }

        this.data = readFromFile();
        this.linesWithKey = findLinesWithKey();


    }


    /**
     * method determines which line is containing keyWord
     * *                     the values are represented with <lineNumber,lineText> pairs
     */
    private Map<Integer, String> findLinesWithKey() {
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
    private List<String> readFromFile() {
        List<String> data = new LinkedList<>();
        try (Scanner scanner = new Scanner(Main.class.getResourceAsStream(WordFinder.fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

    /***
     * method does filtration depended on @param isPart and @param isCaseSensitive
     *
     * @param isPart means that if @param keyWord is a part of another word
     * @param isCaseSensitive is flag determines that the search process will be completed taking into account case sensitivity or not
     * @return filtered data
     */

    private Map<Integer, String> doFiltration(final boolean isPart, final boolean isCaseSensitive) {
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
            }
            // part adn not sensitve
            else if (isPart) {
                return linesWithKey;
            }
        }
        return filteredData;
    }


    private void printer(Map<Integer, String> filteredData, boolean isPart, boolean isCaseSensitive) {
        for (Map.Entry<Integer, String> entry : filteredData.entrySet()) {
            Integer lineNumber = entry.getKey();
            String value = entry.getValue();
            System.out.println(lineNumber + "\t" + value);
            linePrinter(value, isPart, isCaseSensitive);

        }
    }

    private void linePrinter(String text, boolean isPart, boolean isCaseSensitive) {
        System.out.print("\t");
        String[] s = text.split(" ");

        // not part and case sensitive
        if (!isPart && isCaseSensitive) {
            lineModifier(s, notPartAndSensitive);
        }
        // is part and is case sensitive
        else if (isPart && isCaseSensitive) {
            lineModifier(s, partAndSensitive);
        }
        // is part and not case sensitive
        else if (isPart) {
            lineModifier(s, partAndNotSnesitive);
        }
    }

    /**
     * method dynamically determains what kind of symbol to add in console
     * @param s is line for which will determined cursor
     * @param function is lambda method which is connected to CommandLine arguments
     */
    private void lineModifier(String[] s, Function function) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : s) {
            stringBuilder.append(((boolean) function.apply(item) ? "^" : "-").repeat(item.length()));
            stringBuilder.append("-");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        System.out.println(stringBuilder.toString());
    }



    /***
     * params are passed from terminal.
     * this overload reads and finds only keyWord from fileName.
     * the search process will work without "part" and with "case sensitivity"
     */
    public void startWorking() {

        final Map<Integer, String> filteredData = doFiltration(isPart, isSensitive);
        printer(filteredData, isPart, isSensitive);

    }




}
