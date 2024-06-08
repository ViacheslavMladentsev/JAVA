import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ManagerTasks {

    private static final String PATH_OUTPUT_DIRECTORY_FOR_RECORD = ("/home/TrainingProject_12_Multithreading_Download_Files/src/download/");

    private static HashMap<Integer, String> mapLinks = null;

    private static int numberTask = 1;


    public ManagerTasks(final HashMap<Integer, String> mapLinks, final int countThreads) {
        ManagerTasks.mapLinks = mapLinks;
    }


     public void startTask() {
        while (mapLinks.containsKey(numberTask)) {
            int i;
            synchronized (this) {
                i = numberTask;
                ++numberTask;
            }
            try (InputStream in = URI.create(mapLinks.get(i)).toURL().openStream()) {
                System.out.println(Thread.currentThread().getName() + " start download file number " + i);
                Files.copy(in, Paths.get(PATH_OUTPUT_DIRECTORY_FOR_RECORD + Paths.get(mapLinks.get(i)).getFileName()));
                System.out.println(Thread.currentThread().getName() + " finish download file number " + i);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}
