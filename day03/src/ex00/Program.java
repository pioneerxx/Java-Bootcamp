package ex00;

class Program {
    public static void main(String args[]) {
        int count;
        Egg egg;
        Hen hen;
        if (args.length != 1 && !args[0].startsWith("--count=")) {
            System.err.println("Wrong input\nUsage: java ex00.Program --count=*num*");
            System.exit(-1);
        }
        try {
            count = Integer.parseInt(args[0].split("=")[1]);
            egg = new Egg(count);
            hen = new Hen(count);
            egg.start();
            hen.start();
            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }
        } catch (NumberFormatException e) {
            System.err.println("Wrong input");
            System.exit(-1);
        }
    }
}