import java.util.Scanner;

public class Main {

    private static final int MAXLENGTH = 999;

    public static void main(String[] args) {
        DataProcessing();
    }

    private static void DataProcessing() {
        Scanner sc = new Scanner(System.in);
        final char[] str = sc.nextLine().toCharArray();
        sc.close();
        CheckParametr(str);

        int[][] arrayParser = new int[str.length][2];
        int lastIndex = FillAndCount(str, arrayParser);
        int maxCount = SearchMaxCount(arrayParser);

        sc.close();

        SortByCount(arrayParser, lastIndex);
        SortBySymbol(arrayParser, lastIndex);

        int[][] histograms = new int[lastIndex > 10 ? 10 : lastIndex][];
        FillHistogram(arrayParser, histograms, maxCount);
        PrintArray(histograms);
    }

    private static void CheckParametr(char[] inputArray) {
        if (inputArray.length > MAXLENGTH) {
            System.err.println("Illegal Argument");
            System.exit(1);
        }
    }

    private static boolean SearchRepeat(char symbol, int[][] targetArray) {
        for (int[] ints : targetArray) {
            if (ints[0] == symbol) {
                return true;
            }
        }
        return false;
    }

    private static int SearchMaxCount(int[][] targetArray) {
        int maxCount = 0;
        for (int[] ints : targetArray) {
            if (ints[1] > maxCount) {
                maxCount = ints[1];
            }
        }
        return maxCount;
    }

    private static int FillAndCount(char[] targetArray, int[][] arrayParser) {
        for (int i = 0, k = 0; i < targetArray.length; ++i) {
            int countSymbol = 0;

            if (SearchRepeat(targetArray[i], arrayParser)) {
                continue;
            }

            for (char c : targetArray) {
                if (targetArray[i] == c) {
                    ++countSymbol;
                }
            }

            arrayParser[k][0] = targetArray[i];
            arrayParser[k][1] = countSymbol;
            ++k;
        }
        int lastIndex = 0;
        for (int[] ints : arrayParser) {
            if (ints[1] == 0) {
                break;
            }
            ++lastIndex;
        }
        return lastIndex;
    }

    private static void SortByCount(int[][] arrayParser, int lastIndex) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1, j = lastIndex - 1; i < lastIndex; ++i, --j) {
                if (arrayParser[i][1] > arrayParser[i - 1][1]) {
                    isSorted = SortedElements(arrayParser, i);
                }
            }
        }
    }

    private static void SortBySymbol(int[][] arrayParser, int lastIndex) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1, j = lastIndex - 1; i < lastIndex; ++i, --j) {
                if (arrayParser[i][1] == arrayParser[i - 1][1]) {
                    if (arrayParser[i][0] < arrayParser[i - 1][0]) {
                        isSorted = SortedElements(arrayParser, i);
                    }
                }
            }
        }
    }

    private static boolean SortedElements(int[][] arrayParser, int i) {
        int tempCount = arrayParser[i][0];
        int tempSymbol = arrayParser[i][1];
        arrayParser[i][0] = arrayParser[i - 1][0];
        arrayParser[i][1] = arrayParser[i - 1][1];
        arrayParser[i - 1][0] = tempCount;
        arrayParser[i - 1][1] = tempSymbol;
        return false;
    }

    private static void FillHistogram(int[][] arrayParser, int[][] histograms, int maxCount) {
        for (int i = 0; i < histograms.length; ++i) {
            int countSharp = (int) (arrayParser[i][1] / (maxCount / 10.0));
            histograms[i] = new int[countSharp + 2];
            histograms[i][0] = arrayParser[i][0];
            for (int j = 1; j <= countSharp; ++j) {
                histograms[i][j] = '#';
            }
            histograms[i][countSharp + 1] = arrayParser[i][1];
        }
    }

    private static void PrintArray(int[][] histograms) {
        System.out.println();
        for (int i = histograms[0].length - 1; i >= 0; --i) {
            for (int j = 0; j < histograms.length; ++j) {
                if (j == 0) {
                    if (i == histograms[j].length - 1) {
                        System.out.printf("%3d ", histograms[j][i]);
                    } else {
                        System.out.printf("%3c ", histograms[j][i]);
                    }
                } else if (histograms[j].length - 1 >= i) {
                    if (i == histograms[j].length - 1) {
                        System.out.printf("%3d ", histograms[j][i]);
                    } else {
                        System.out.printf("%3c ", histograms[j][i]);
                    }
                } else {
                    break;
                }
            }
            System.out.println();
        }
    }
}
