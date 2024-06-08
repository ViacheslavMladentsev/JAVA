import java.util.HashMap;

public class ThreadsFactory {

    private ThreadsFactory() {
    }


    public static void startFactory(final ManagerTasks manager, final int countThreads) {
        for (int i = 0; i < countThreads; ++i) {
            RunnerSavePictureByURL task = new RunnerSavePictureByURL(manager);
            Thread thread = new Thread(task);
            thread.start();
        }
    }

}
