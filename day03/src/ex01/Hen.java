package ex01;

public class Hen extends Thread {
    private int count;
    private ProducerConsumer prodCon;
    public Hen(int count, ProducerConsumer prodCon) {
        this.count = count;
        this.prodCon = prodCon;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                prodCon.consume();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
