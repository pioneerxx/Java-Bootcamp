package ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

class NotDirectoryException extends RuntimeException {}

public class Finder {
    File folder;
    Scanner scanner;
    public Finder(String path) throws NotDirectoryException {
        folder = new File(path);
        if (!folder.isDirectory()) {
            throw new NotDirectoryException();
        }
        scanner = new Scanner(System.in);
    }
    void startFinder() {
        System.out.println(folder.toPath());
        String prompt;
        while (true) {
            System.out.print("-> ");
            prompt = scanner.next();
            switch(prompt) {
                case "mv":
                    moveFile();
                    break;
                case "ls":
                    listFiles();
                    break;
                case "cd":
                    changeDirectory();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                case "pwd":
                    System.out.println(folder.getAbsolutePath());
                    break;
                default:
                    System.out.println("Unknown command: " + prompt);
                    break;
            }
        }
    }

    private void moveFile() {
        File oldFile;
        File newFile;
        String oldFileName;
        String newFileName;
        InputStream inputStream;
        OutputStream outputStream;
        oldFileName = scanner.next();
        newFileName = scanner.next();
        oldFileName = oldFileName.startsWith("/") ? oldFileName : folder.getAbsolutePath() + '/' + oldFileName;
        newFileName = newFileName.startsWith("/") ? newFileName : folder.getAbsolutePath() + '/' + newFileName;
        oldFile = new File(oldFileName);
        newFile = new File(newFileName);
        try {
            if (oldFile.exists() && newFile.createNewFile()) {
                inputStream = new FileInputStream(oldFile);
                outputStream = new FileOutputStream(newFile);
                outputStream.write(inputStream.readAllBytes());
                oldFile.delete();
                inputStream.close();
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
        } catch (IOException e) {
            System.out.println("Error while reading or writing");
            newFile.delete();
        }
    }

    private void listFiles() {
        long size;
        String weight;
        for (File entry : folder.listFiles()) {
            if (entry.isFile()) {
                size = entry.length();
                weight = "bytes";
                if (size > 1024) {
                    size /= 1024;
                    weight = "KB";
                }
                if (size > 1024) {
                    size /= 1024;
                    weight = "MB";
                }
                System.out.println(entry.getName() + ' ' + size + ' ' + weight);
            } else {
                System.out.println(entry.getName());
            }
        }
    }

    private void changeDirectory() {
        String newPath;
        File temp;
        if (scanner.hasNext()) {
            newPath = scanner.next();
            newPath = newPath.startsWith("/") ? newPath : folder.getAbsolutePath() + '/' + newPath;
            temp = new File(newPath);
            if (temp.isDirectory()) {
                folder = new File(newPath);
            } else {
                System.out.println("Not a directory");
            }
        }
    }
}
