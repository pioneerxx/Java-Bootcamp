package ex02;

public class Program {
    public static void main(String args[]) {
        int arraySize;
        int threadsCount;
        int[] array;
        int sectionCount;
        int tmp = 0;
        int sum = 0;
        SumThread[] threadArray;
        ProducerConsumer prodCon;
        try {
            if (args.length != 2 || !args[0].startsWith("--arraySize=") 
            || !args[1].startsWith("--threadsCount=")) {
                System.err.println("Wrong input");
                System.err.println("Usage: java ex02.Program --arraySize=*num* --threadsCount=*num*");
                System.exit(-1);
            }
            arraySize = Integer.parseInt(args[0].split("=")[1]);
            threadsCount = Integer.parseInt(args[1].split("=")[1]);
            if (threadsCount > arraySize) {
                System.err.println("Threads count can't exceed arrays size");
                System.exit(-1);
            }
            sectionCount = arraySize / threadsCount;
            array = new int[arraySize];
            threadArray = new SumThread[threadsCount];
            prodCon = new ProducerConsumer(threadsCount);
            for (int i = 0; i < arraySize; i++) {
                array[i] = (int)(Math.random() * 2000 - 1000);
            }
            for (int i = 0; i < threadsCount; i++) {
                if (((i * sectionCount + i) > arraySize) || i == threadsCount - 1) {
                    tmp = arraySize;
                } else {
                    tmp = i * sectionCount + sectionCount;
                }
                threadArray[i] = new SumThread(array, i * sectionCount, tmp, prodCon);
                threadArray[i].setName("Thread " + (i + 1));
                threadArray[i].start();
            }
            prodCon.getFullSum();
            for (int i = 0; i < arraySize; i++) {
                sum += array[i];
            }
            System.out.println("Sum by standard: " + sum);
        } catch (NumberFormatException e) {
            System.err.println("Wrong input");
            System.err.println("Usage: java ex02.Program --arraySize=*num* --threadsCount=*num*");
            System.exit(-1);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Wrong input");
            System.err.println("Usage: java ex02.Program --arraySize=*num* --threadsCount=*num*");
            System.exit(-1);
        } catch (InterruptedException e) {
            System.out.println("Wait was interrupted");
        }
    }
}
