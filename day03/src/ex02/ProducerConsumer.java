package ex02;

public class ProducerConsumer {
    private static int sum = 0;
    private static int countDone = 0;
    private int threadsCount;
    public ProducerConsumer(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    synchronized void refresh(int sumSection, int left, int right) {
        sum += sumSection;
        countDone++;
        System.out.println("Thread " + countDone + ": from " + left + 
        " to " + (right - 1) + " sum is " + sumSection);
        notify();
    }

    public int sumSection(int[] arr, int left, int right) {
        int sumSection = 0;
        for (int i = left; i < right; i++) {
            sumSection += arr[i]; 
        }
        refresh(sumSection, left, right);
        return sumSection;
    }

    synchronized void getFullSum() throws InterruptedException {
        while (countDone != threadsCount) {
            wait();
        }
        System.out.println("Sum by threads: " + sum);
    }
}
