package ex01;

public class Egg extends Thread {
    private int count;
    private ProducerConsumer prodCon;
    public Egg(int count, ProducerConsumer prodCon) {
        this.count = count;
        this.prodCon = prodCon;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                prodCon.produce();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
