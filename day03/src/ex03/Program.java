package ex03;

import java.io.FileNotFoundException;

public class Program {
    public static void main(String args[]) {
        int threadsCount;
        ProducerConsumer prodCon;
        FileDownloaderThread[] threads;
        try {
            if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
                System.err.println("Wrong input");
                System.exit(-1);
            }
            threadsCount = Integer.parseInt(args[0].split("=")[1]);
            threads = new FileDownloaderThread[threadsCount];
            prodCon = new ProducerConsumer(threadsCount);
            for (int i = 0; i < threadsCount; i++) {
                threads[i] = new FileDownloaderThread(prodCon);
            }
            for (FileDownloaderThread i : threads) {
                i.start();
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Wrong input");
            System.err.println("Usage: java ex03.Program --threadsCount=*num*");
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.err.println("Wrong input");
            System.err.println("Usage: java ex03.Program ---threadsCount=*num*");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            System.err.println("files_urls.txt file not found");
            System.exit(-1);
        }
    }
}
