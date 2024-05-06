package ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class Program {
    public static void main(String args[]) {
        HashMap<String, String> signatureMap = new HashMap<>();
        try {
            scanSignatureFile(signatureMap);
            verifyFileExtensions(signatureMap);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void scanSignatureFile(HashMap<String, String> map) throws FileNotFoundException, IOException {
        FileInputStream inputStream;
        Scanner scanner;
        String line;
        String[] lineDivided = new String[2];
        inputStream = new FileInputStream("ex00/signatures.txt");
        scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            lineDivided = line.split(", ");
            map.put(lineDivided[0], lineDivided[1]);
        }
        scanner.close();
        inputStream.close();
    }

    public static void verifyFileExtensions(HashMap<String, String> map) throws FileNotFoundException, IOException {
        FileInputStream inputStream;
        FileOutputStream outputStream = new FileOutputStream("ex00/result.txt", true);
        String fileName;
        StringBuffer buf;
        boolean isProccessed;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("-> ");
            fileName = scanner.next();
            if (fileName.equals("42")) {
                break;
            }
            isProccessed = false;
            inputStream = new FileInputStream(fileName);
            buf = new StringBuffer();
            for (int i = 0; i < 8; i++) {
                buf.append(String.format("%02X ", inputStream.read()));
            }
            inputStream.close();
            for (String k : map.keySet()) {
                if (buf.toString().startsWith(map.get(k))) {
                    outputStream.write(k.getBytes());
                    outputStream.write('\n');
                    System.out.println("PROCCESSED");
                    isProccessed = true;
                    break;
                }
            }
            if (!isProccessed) {
                System.out.println("Unknown file extension");
            }
        }
        scanner.close();
        outputStream.close();
    }
}