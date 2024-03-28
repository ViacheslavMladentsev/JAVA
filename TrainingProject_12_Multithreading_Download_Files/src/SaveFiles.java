import java.util.HashMap;

public class SaveFiles {

    private SaveFiles() {
    }


    public static void saveFilesFromUrlToDirectory (final String[] args) {
        int countThreads = ValidationProgramParametrs.validation(args);
        HashMap<Integer, String> mapLinks = Parser.parseLinks();
        ManagerTasks manager = new ManagerTasks(mapLinks, countThreads);
        ThreadsFactory.startFactory(manager, countThreads);
    }

}
