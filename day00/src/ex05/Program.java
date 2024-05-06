import java.util.Scanner;

class Program {
    public static short DayOfWeek(String day) {
        short res = 0;
        switch(day) {
            case "TU":
                res = 0;
                break;
            case "WE":
                res = 1;
                break;
            case "TH":
                res = 2;
                break;
            case "FR":
                res = 3;
                 break;
            case "SA":
                res = 4;
                break;
            case "SU":
                res = 5;
                break;
            case "MO":
                res = 6;
                break;
            }
        return res;
    }

    public static String WeekDay(int num) {
        String res = null;
        switch(num) {
            case 0:
                res = "TU";
                break;
                case 1:
                res = "WE";
                 break;
                 case 2:
                res = "TH";
                break;
                case 3:
                res = "FR";
                break;
                case 4:
                res = "SA";
                break;
                case 5:
                res = "SU";
                break;
                case 6:
                res = "MO";
                break;
            }
        return res;
    }
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String[] students = new String[10];
        short[] weekdays = new short[7];
        short[][] classesTimes = new short[7][5];
        short[][][] attendance;
        String StringTmp;
        short date;
        short studentCount = 0;
        short classCount = 0;
        short classTime;
        short MaxClassesInDay = 0;
        short IntTmp = 0;
         while (studentCount < 10 && !(StringTmp = scanner.next()).equals(".")) {
            students[studentCount] = StringTmp;
            studentCount++;
            if (studentCount == 10) {
                System.out.println('.');
            }
        }
        while (classCount < 10 && !(StringTmp = scanner.next()).equals(".")) {
            classTime = (short)Integer.parseInt(StringTmp);
            IntTmp = DayOfWeek(scanner.next());
            weekdays[IntTmp]++;
            if (classesTimes[IntTmp][classTime - 1] < 1) {
                classesTimes[IntTmp][classTime - 1]++;
                classCount++;
            }
            if (classCount == 10) {
                System.out.println('.');
            }
        }
        for (short i = 0; i < weekdays.length; i++) {
            if (weekdays[i] > MaxClassesInDay) {
                MaxClassesInDay = weekdays[i];
            }
        }
        attendance = new short[30][5][studentCount];
        while (!(StringTmp = scanner.next()).equals(".")) {
            for (short i = 0; i < studentCount; i++) {
                if (StringTmp.equals(students[i])) {
                    IntTmp = i;
                    break;
                }
            }
            classTime = (short)scanner.nextInt();
            date = (short)scanner.nextInt();
            attendance[date - 1][classTime - 1][IntTmp] = scanner.next().equals("HERE") ? (short)1 : -1;
        }
        scanner.close();
        Print(MaxClassesInDay, studentCount, students, attendance, weekdays, classesTimes);
    }

    public static void Print(short MaxClassesInDay, short studentCount, String students[], short attendance[][][], short weekdays[], short classesTimes[][]) {
        boolean wasBefore, isNow;
        short IntTmp;
        for (short i = 0; i < MaxClassesInDay; i++) {
            System.out.print("          ");
            for (short j = 0; j < 30; j++) {
                if (weekdays[j%7] == 0) {
                    continue;
                }
                IntTmp = i;
                wasBefore = false;
                isNow = false;
                for (short k = 0; k < 5; k++) {
                    if (classesTimes[j % 7][k] != 0 && IntTmp == 0) {
                        System.out.printf("%7s", (k + 1) + ":00 " + WeekDay(j%7));
                        System.out.printf(" %2d|", j + 1);
                        isNow = true;
                        break;
                    } else if (classesTimes[j % 7][k] != 0 && IntTmp != 0) {
                        IntTmp--;
                        wasBefore = true;
                    }
                }
                if (wasBefore && !isNow) {
                    System.out.print("          |");
                }
            }
            System.out.println();
            for (short j = 0; j < studentCount; j++) {
                System.out.printf("%10s", students[j]);
                for (short k = 0; k < 30; k++) {
                    if (weekdays[k % 7] == 0) {
                        continue;
                    }
                    IntTmp = i;
                    for (short m = 0; m < 5; m++) {
                        if (classesTimes[k % 7][m] != 0 && IntTmp == 0) {
                            IntTmp = m;
                            break;
                        } else if (classesTimes[k % 7][m] != 0 && IntTmp != 0) {
                            IntTmp--;
                        }
                    }
                    if (attendance[k][IntTmp][j] == 0) {
                        System.out.print("          |");
                    } else {
                        System.out.printf("        %2d|", attendance[k][IntTmp][j]);
                    }
                }
                    System.out.println();
            }
        }
    }
}