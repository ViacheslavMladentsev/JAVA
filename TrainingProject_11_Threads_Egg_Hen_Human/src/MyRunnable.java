class MyRunnable implements Runnable {

    private final int maxIteration;

    private String str = null;


    public MyRunnable(int maxIteration, String str) {
        this.maxIteration = maxIteration;
        this.str = str;
    }


    @Override
    public void run() {
        for (int i = 0; i < maxIteration; ++i) {
            System.out.println(str);
        }
    }

}