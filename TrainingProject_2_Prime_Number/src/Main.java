import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CheckedSimpleNumber();
    }

    private static void CheckedSimpleNumber() {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        sc.close();
        boolean isSimple = true;
        int count_loop = 1;

        if (number < 2) {
            System.err.println("Illegal Argument");
            System.exit(1);
        } else if (number > 3) {
            for (int i = 2; i <= number / 2; ++i, ++count_loop) {
                if (number % i == 0) {
                    isSimple = false;
                    break;
                } else {
                    if (i * i >= number) {
                        break;
                    }
                }
            }
        }
        System.out.printf("%b %d\n", isSimple, count_loop);
    }
}
