import java.util.Scanner;

class Program {

    public static int DigitSum(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num = num / 10;
        }
        return res;
    }

    public static boolean isPrime(int num) {
        boolean isPrime = true;
        if (num <= 1) {
            System.err.println("Illegal argument");
            System.exit(-1);
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int CoffeeQueries = 0;
        int query = 0;
        while (query != 42) {
            query = scanner.nextInt();
            CoffeeQueries += isPrime(DigitSum(query)) ? 1 : 0;
        }
        System.out.println("Count of coffee-request - " + CoffeeQueries);
        scanner.close();
    }
}