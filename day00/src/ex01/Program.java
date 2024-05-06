import java.util.Scanner;

class Program {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.close();
        int iters = 0;
        boolean isPrime = true;
        if (num <= 1) {
            System.err.println("Illegal argument");
            System.exit(-1);
        }
        for (int i = 2; i * i <= num; i++) {
            iters++;
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println(isPrime ? "true " + iters : "false " + iters);
    }
}