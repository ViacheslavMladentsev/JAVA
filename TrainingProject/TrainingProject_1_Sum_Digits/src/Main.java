public class Main {
    public static void main(String[] args) {
        System.out.println(SumDigits(126534));
    }

    private static int SumDigits(int number) {
        int result = 0;

        for (int i = 0; i < 5; ++i) {
            int tempDigit = number % 10;
            result += tempDigit;
            number /= 10;
        }

        result += number;
        return result;
    }
}
