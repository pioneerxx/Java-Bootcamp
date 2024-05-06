package ex01;

public class ProducerConsumer {
    private enum Answer {
        EGG, HEN
    };

    private static Answer answer = Answer.EGG;

    synchronized void produce() throws InterruptedException {
        while (answer != Answer.EGG) {
            wait();
        }
        System.out.println(answer);
        answer = Answer.HEN;
        notify();
    }

    synchronized void consume() throws InterruptedException {
        while (answer != Answer.HEN) {
            wait();
        }
        System.out.println(answer);
        answer = Answer.EGG;
        notify();
    }
}
