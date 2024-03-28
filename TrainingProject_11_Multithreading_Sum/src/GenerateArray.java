import java.util.Arrays;

public class GenerateArray {

    private static final int MIN_RANDOM_NUMBER = -1000;

    private static final int MAX_RANDOM_NUMBER = 1000;


    private final int[] array;


    public GenerateArray(final int sizeArray) {
        this.array = new int[sizeArray];
        initializationRandomNumbers();
    }


    public int[] getArray() {
        return array;
    }


    private void initializationRandomNumbers() {
        for (int i = 0; i < this.array.length; ++i) {
            this.array[i] = randomNumberInRange(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
        }
    }

    private static int randomNumberInRange(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}
