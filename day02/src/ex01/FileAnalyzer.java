package ex01;

import java.io.*;
import java.util.*;

public class FileAnalyzer {
    private BufferedReader reader;
    private BufferedWriter writer;

    public FileAnalyzer() {}
    public void analyzeFile(String filename, HashMap<String, Integer> map, HashMap<String, Integer> commonMap) throws FileNotFoundException, IOException {
        String[] line;
        reader = new BufferedReader(new FileReader(filename));
        while (reader.ready()) {
            line = reader.readLine().split(" ");
            for (String i: line) {
                if (map.containsKey(i)) {
                    commonMap.put(i, map.get(i) + 1);
                    map.put(i, map.get(i) + 1);
                } else {
                    commonMap.put(i, 1);
                    map.put(i, 1);
                }
            }
        }
        reader.close();
    }

    public HashMap<String, Integer> getOrderedMap(HashMap<String, Integer> commonMap) {
        HashMap<String, Integer> orderedMap = new HashMap<>();
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>();
        for (Map.Entry<String, Integer> i : commonMap.entrySet()) {
            list.add(i);
        }
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String, Integer> i : list) {
            orderedMap.put(i.getKey(), i.getValue());
        }
        return orderedMap;
    }

    public void fillVectors(Vector<Integer> vector1, Vector<Integer> vector2, HashMap<String, Integer> map1, HashMap<String, Integer> map2, HashMap<String, Integer> orderedMap) {
        for (String key : orderedMap.keySet()) {
            if (map1.containsKey(key)) {
                vector1.add(map1.get(key));
            } else {
                vector1.add(0);
            }
            if (map2.containsKey(key)) {
                vector2.add(map2.get(key));
            } else {
                vector2.add(0);
            }
        }
    }

    public double calculateSimilarity(Vector<Integer> vector1, Vector<Integer> vector2) {
        int numerator = 0;
        double denominator = 0;
        int squareSumFirst = 0;
        int squareSumSecond = 0;
        for (int i = 0; i < vector1.size(); i++) {
            numerator += vector1.get(i) * vector2.get(i);
            squareSumFirst += vector1.get(i) * vector1.get(i);
            squareSumSecond += vector2.get(i) * vector2.get(i);
        }
        denominator = Math.sqrt(squareSumFirst) * Math.sqrt(squareSumSecond);
        return numerator / denominator;
    }

    public void saveDictionary(HashMap<String, Integer> commonMap) throws FileNotFoundException, IOException {
        writer = new BufferedWriter(new FileWriter("ex01/dictionary.txt"));
        writer.write(commonMap.toString());
        writer.close();
    }
}
