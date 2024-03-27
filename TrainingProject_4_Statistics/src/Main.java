import java.util.Scanner;

public class Main {

    final static String EXITPROGRAM = "999";

    private static int countWeek = 1;
    private static int containerMinScore = 0;

    public static void main(String[] args) {
        DataProcessing();
    }

    private static void DataProcessing() {
        Scanner sc = new Scanner(System.in);

        String week = sc.next();
        if (week.equals(EXITPROGRAM)) {
            System.exit(0);
        }
        CheckFirstParametr(week);
        CheckSecondParametr(sc);
        int currentWeek = sc.nextInt();

        for (; countWeek <= 18; ++countWeek) {
            CheckOrderWeeks(currentWeek);
            containerMinScore = containerMinScore * 10 + SearchMinScore(sc);

            week = sc.next();
            if (week.equals(EXITPROGRAM)) {
                break;
            }
            CheckFirstParametr(week);

            CheckSecondParametr(sc);
            currentWeek = sc.nextInt();
        }
        PrintGraph();
        sc.close();
    }

    private static void CheckFirstParametr(String week) {
        if (!week.equals("Week")) {
            System.err.println("Illegal Argument");
            System.exit(1);
        }
    }

    private static void CheckSecondParametr(Scanner sc) {
        if (!sc.hasNextInt()) {
            System.err.println("Illegal Argument");
            System.exit(1);
        }
    }

    private static void CheckOrderWeeks(int currentWeek) {
        if (currentWeek != countWeek) {
            System.err.println("Illegal Argument");
            System.exit(1);
        }
    }

    private static int SearchMinScore(Scanner sc) {
        int minScore = 9;
        for (int i = 0; i < 5; ++i) {
            CheckSecondParametr(sc);
            int currentScore = sc.nextInt();
            CheckScores(currentScore);
            if (currentScore < minScore) {
                minScore = currentScore;
            }
        }
        return minScore;
    }

    private static void CheckScores(int currentScore) {
        if (!(currentScore > 0 && currentScore < 10)) {
            System.err.println("Illegal Argument");
            System.exit(1);
        }
    }

    private static void PrintGraph() {
        int scale = GetScale();
        for (int i = 1; i <= countWeek; ++i, scale /= 10) {
            System.out.printf("Week %d ", i);

            for (int j = 0; j < (containerMinScore / scale); ++j) {
                System.out.print("=");
            }
            System.out.println(">");
            containerMinScore %= scale;
        }
    }

    private static int GetScale() {
        int result = 1;
        for (int i = 1; i < countWeek; ++i) {
            result *= 10;
        }
        return result;
    }
}
