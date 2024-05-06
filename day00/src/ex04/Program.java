import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        short[] charCount = new short[65536];
        for (char c : input.toCharArray()) {
            charCount[(int)c]++;
        }
        int[] topCharCount = new int[10];
        char[] topCharacters = new char[10];
        short tmp = 0;
        for (int i = 0; i < charCount.length; i++) {
            if (charCount[i] == 0) {
                continue;
            }
            tmp = charCount[i];
            for (int j = 0; j < topCharCount.length; j++) {
                if (tmp > topCharCount[j]) {
                    for (int k = topCharCount.length - 1; k > j; k--) {
                        topCharCount[k] = topCharCount[k - 1];
                        topCharacters[k] = topCharacters[k - 1];
                    }
                    topCharCount[j] = tmp;
                    topCharacters[j] = (char)i;
                    break;
                } else if (tmp == topCharCount[j] && i < (int)topCharacters[j]) {
                    for (int k = topCharCount.length - 1; k > j; k--) {
                        topCharacters[k] = topCharacters[k - 1];
                    }
                    topCharacters[j] = (char)i;
                    break;
                }
            }
        }
        int topCount = topCharCount[0];
        System.out.println("\n\n");
        for (int i = 0; i < 10; i++) {
            if (topCharCount[i] == topCount) {
                System.out.print(topCharCount[i] + "\t");
            }
        }
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if ((int)(topCharCount[j] * 10 / topCount) >= i)
                    System.out.print("#\t");
                if ((int)(topCharCount[j] * 10 / topCount) == i - 1) {
                    if (topCharCount[j] != 0) {
                        System.out.print(topCharCount[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            if (topCharacters[i] == '\u0000') {
                break;
            }
            System.out.print(topCharacters[i] + "\t");
        }
    }
}