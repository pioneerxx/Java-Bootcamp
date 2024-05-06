package ex02;

public class SumThread extends Thread {
    private int[] arr;
    private int left;
    private int right;
    private ProducerConsumer prodCon;
    public SumThread(int[] arr, int left, int right, ProducerConsumer prodCon) {
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.prodCon = prodCon;
    }

    @Override
    public void run() {
        prodCon.sumSection(arr, left, right);
    }
}
