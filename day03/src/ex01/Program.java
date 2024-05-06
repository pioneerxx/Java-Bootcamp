package ex01;

public class Program {
    public static void main(String args[]) {
        int count;
        ProducerConsumer prodCon;
        Egg egg;
        Hen hen;
        if (args.length != 1) {
            System.err.println("Wrong input\nUsage: java ex00.Program --count=*num*");
            System.exit(-1);
        }
        if (!args[0].startsWith("--count=")) {
            System.err.println("Wrong input\nUsage: java ex00.Program --count=*num*");
            System.exit(-1);
        }
        try {
            count = Integer.parseInt(args[0].split("=")[1]);
            prodCon = new ProducerConsumer();
            egg = new Egg(count, prodCon);
            hen = new Hen(count, prodCon);
            egg.start();
            hen.start();
        } catch (RuntimeException e) {
            System.err.println("Error");
            System.exit(-1);
        } 
    }
}
