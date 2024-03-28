import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Parser {

    private static final String FORMAT_MESSAGE_FOR_ERROR = "Ошибка: %S.\nПерезапустите программу.";

    private static final Path PATH_LIST_LINKS_FOR_LOADED = Paths.get("/home/TrainingProject_12_Multithreading_Download_Files/src/files_urls.txt");

    private static final HashMap<Integer, String> mapLinks = new HashMap<>();


    private Parser() {
    }


    public static HashMap<Integer, String> parseLinks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_LIST_LINKS_FOR_LOADED.toFile()))) {
            addLinksToMap(reader);
        } catch (IOException e) {
            System.out.printf(FORMAT_MESSAGE_FOR_ERROR, e);
        }

        return mapLinks;
    }

    private static void addLinksToMap(final BufferedReader reader) {
        String line = null;
        while (true) {
            try {
                if ((line = reader.readLine()) == null)
                    break;
            } catch (IOException e) {
                System.out.printf(FORMAT_MESSAGE_FOR_ERROR, e);
            }
            String[] temp = line.split(" ");
            mapLinks.put(Integer.parseInt(temp[0]), temp[1]);
        }
    }

}
