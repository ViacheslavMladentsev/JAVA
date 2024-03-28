public class CalculationManager {

    private static int result = 0;
    private static int stepByEqualLoop = 0;
    private static int positionByLastLoop = 0;


    private CalculationManager() {
    }


    public static void sumElementsArrayByMultithreading(final String[] args) {
        Parser parseParametrs = new Parser(args);
        GenerateArray myArray = new GenerateArray(parseParametrs.getSizeArray());

        System.out.println("Сумма: " + sumAllElementArray(myArray.getArray()));

        calculatingCountLoop(parseParametrs.getCountThread(), parseParametrs.getSizeArray());

        threadsFactory(myArray.getArray(), parseParametrs.getCountThread());
        System.out.println("Сумма по потокам: " + result);
    }

    private static int sumAllElementArray(final int[] myArray) {
        int result = 0;
        for (int j : myArray) {
            result += j;
        }
        return result;
    }

    private static int randomNumberInRange(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private static void calculatingCountLoop(final int countThread, final int sizeArray) {
        stepByEqualLoop = randomNumberInRange(1, sizeArray / countThread + 1);
        for (int i = 1; i < countThread; ++i) {
            positionByLastLoop += stepByEqualLoop;
        }
    }

    private static void threadsFactory(final int[] myArray, final int countThread) {
        for (int i = 0, k = 0; i < countThread - 1; ++i, k += stepByEqualLoop) {
            result += creatThread(myArray, k, k + stepByEqualLoop);
        }
        result += creatThread(myArray, positionByLastLoop, myArray.length);
    }

    private static int creatThread(final int[] myArray, final int from, final int to) {
        ThreadSummationElementsInRange task = new ThreadSummationElementsInRange(myArray, from, to);
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return task.getResult();
    }

}
