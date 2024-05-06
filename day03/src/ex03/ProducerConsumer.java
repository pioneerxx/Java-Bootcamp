package ex03;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProducerConsumer {
    private static List<String> files;
    private static int lastFileNumber = 0;
    private static int availableThreadCount;

    public ProducerConsumer(int threadCount) throws FileNotFoundException {
        availableThreadCount = threadCount;
        Scanner scanner = new Scanner(new FileInputStream("ex03/files_urls.txt"));
        files = new LinkedList<String>();
        while (scanner.hasNextLine()) {
            files.add(scanner.nextLine().split(" ")[1]);
        }
        scanner.close();
    }

    void downloadFile(String threadName) throws MalformedURLException, IOException, InterruptedException {
        int currentFileNumber;
        while (availableThreadCount == 0) {
            wait();
        }
        synchronized (this) {
            availableThreadCount--;
            currentFileNumber = lastFileNumber;
            lastFileNumber++;
        }
        FileOutputStream outputStream;
        URL fileURL;
        BufferedInputStream in;
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        System.out.println(threadName + " start download file number " + (currentFileNumber + 1));
        fileURL = new URL(files.get(currentFileNumber));
        in = new BufferedInputStream((fileURL).openStream());
        outputStream = new FileOutputStream(fileURL.getFile().substring(fileURL.getFile().lastIndexOf('/') + 1));
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            outputStream.write(dataBuffer, 0, bytesRead);
        }
        outputStream.close();
        in.close();
        System.out.println(threadName + " finish download file number " + (currentFileNumber + 1));
        synchronized (this) {
            availableThreadCount++;
        }
    }

    public synchronized int getLastFileNumber() {
        return lastFileNumber;
    }

    public int getFileListSize() {
        return files == null ? 0 : files.size();
    }
}
