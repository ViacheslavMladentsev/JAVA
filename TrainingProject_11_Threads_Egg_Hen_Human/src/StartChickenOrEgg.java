public class StartChickenOrEgg {

    private StartChickenOrEgg() {
    }


    public static void startProgram(String[] args) {
        int countRepeat = ValidInputArgument.checkedParametrs(args);

        MyRunnable myRunnable1 = new MyRunnable(countRepeat, "Egg");
        Thread thread1 = new Thread(myRunnable1);
        thread1.start();

        MyRunnable myRunnable2 = new MyRunnable(countRepeat, "Hen");
        Thread thread2 = new Thread(myRunnable2);
        thread2.start();

        printFiftyIterations(countRepeat, "Humman");
    }

    private static void printFiftyIterations(final int maxIteration, final String str) {
        for (int i = 0; i < maxIteration; ++i) {
            System.out.println(str);
        }
    }

}
