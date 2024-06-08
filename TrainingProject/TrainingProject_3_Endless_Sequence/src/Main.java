import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalculationCountSimpleNumber();
    }

    private static void CalculationCountSimpleNumber() {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        int countOfCoffeeRequest = 0;
        for (; number != 999; number = sc.nextInt()) {
            int resultSumDigit = SumDigits(number);
            if (CheckSimpleDigit(resultSumDigit)) {
                ++countOfCoffeeRequest;
            }
        }
        System.out.printf("Count of simple number – %d\n", countOfCoffeeRequest);
        sc.close();
    }

    public static int SumDigits(int number) {
        int result = 0;

        for (int i = 0; i < 5; ++i) {
            int tempDigit = number % 10;
            result += tempDigit;
            number /= 10;
        }

        result += number;
        return result;
    }

    private static boolean CheckSimpleDigit(int number) {
        boolean isSimple = true;
        if (number < 2) {
            System.err.println("Illegal Argument");
            System.exit(1);
        } else if (number > 3) {
            for (int i = 2; i <= number / 2; ++i) {
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
        return isSimple;
    }
}
