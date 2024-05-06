package ex05;

class Program {
    public static void main(String args[]) {
        Menu menu = new Menu();
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            menu.StartDev();
        } else if (args.length > 0 && args[0].equals("--profile=prod")) {
            menu.StartProd();
        } else {
            System.err.println("Please specify the mode for running\nUsage: --profile[dev, prod]");
            System.exit(-1);
        }
    }
}