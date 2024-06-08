public class ThreadSummationElementsInRange implements Runnable {

    private static final String FORMAT_OUT_PRINT_THREAD = "%s: от %d до %d сумма равна %d\n";

    private final int FROM;

    private final int TO;

    private final int[] myArray;

    private int result;


    public ThreadSummationElementsInRange(final int[] myArray, final int from, final int to) {
        this.myArray = myArray;
        this.FROM = from;
        this.TO = to;
    }

    @Override
    public void run() {
        sumAllElementArrayByRange();
        System.out.printf(FORMAT_OUT_PRINT_THREAD, Thread.currentThread().getName(), this.FROM, this.TO - 1, this.result);
    }


    public int getResult() {
        return result;
    }


    private int sumAllElementArrayByRange() {
        for (int i = this.FROM; i < this.TO; ++i) {
            this.result += myArray[i];
        }
        return this.result;
    }

}
