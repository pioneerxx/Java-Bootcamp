package ex01;
import java.util.*;
import java.io.*;

class Program {
    public static void main(String args[]) {
        HashMap<String, Integer> firstDictionary;
        HashMap<String, Integer> secondDictionary;
        HashMap<String, Integer> commonDictionary;
        HashMap<String, Integer> orderedDictionary;
        Vector<Integer> firstVector;
        Vector<Integer> secondVector;
        FileAnalyzer analyzer;
        if (args.length != 2) {
            System.err.println("Incorrect input");
            System.err.println("Usage: java Program file1.txt file2.txt");
            System.exit(-1);
        }
        try {
            firstDictionary = new HashMap<String, Integer>();
            secondDictionary = new HashMap<String, Integer>();
            commonDictionary = new HashMap<String, Integer>();
            analyzer = new FileAnalyzer();
            analyzer.analyzeFile(args[0], firstDictionary, commonDictionary);
            analyzer.analyzeFile(args[1], secondDictionary, commonDictionary);
            if (firstDictionary.size() == 0 || secondDictionary.size() == 0) {
                System.err.println("One or more files are empty");
                System.exit(-1);
            }
            orderedDictionary = analyzer.getOrderedMap(commonDictionary);
            analyzer.saveDictionary(orderedDictionary);
            firstVector = new Vector<>();
            secondVector = new Vector<>();
            analyzer.fillVectors(firstVector, secondVector, firstDictionary, secondDictionary, orderedDictionary);
            System.out.println(Math.floor(analyzer.calculateSimilarity(firstVector, secondVector) * 100) / 100);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}