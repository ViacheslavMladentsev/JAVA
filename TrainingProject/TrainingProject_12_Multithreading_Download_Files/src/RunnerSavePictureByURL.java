public class RunnerSavePictureByURL implements Runnable {

    private ManagerTasks manager = null;


    public RunnerSavePictureByURL(final ManagerTasks manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        this.manager.startTask();
    }

}
