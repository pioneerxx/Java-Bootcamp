package ex03;

import java.io.IOException;
import java.net.MalformedURLException;

public class FileDownloaderThread extends Thread {
    private ProducerConsumer prodCon;
    
    public FileDownloaderThread(ProducerConsumer prodCon) {
        this.prodCon = prodCon;
    }

    @Override
    public void run() {
        try {
            while (prodCon.getLastFileNumber() != prodCon.getFileListSize()) {
                prodCon.downloadFile(getName());
            }
        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
