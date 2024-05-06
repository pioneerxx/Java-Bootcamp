import java.util.Scanner;

class Program {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        int WeekNum = 1;
        int pow = 1;
        long lowestGrades = 0;
        int lowestGrade = 10;
        int tmp = 0;
        while (!input.equals("42") && WeekNum <= 18) {
            if (!input.equals("Week") || scanner.nextInt() != WeekNum) {
                scanner.close();
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            for (int i = 0; i < 5; ++i) {
                if (!scanner.hasNextInt()) {
                    scanner.close();
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
                tmp = scanner.nextInt();
                if (tmp > 9 || tmp < 1) {
                    scanner.close();
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
                lowestGrade = tmp < lowestGrade ? tmp : lowestGrade;
            }
            pow = 1;
            for (int i = 0; i < WeekNum - 1; i++) {
                pow *= 10;
            }
            lowestGrades += pow * lowestGrade;
            lowestGrade = 9;
            WeekNum++;
            input = scanner.next();
        }
        for (int i = 1; i < WeekNum; i++) {
            System.out.print("Week" + i);
            tmp = (int)(lowestGrades % 10);
            lowestGrades /= 10;
            while (tmp > 0) {
                System.out.print('=');
                tmp--;
            }
            System.out.println('>');
        }
        scanner.close();
    }
}