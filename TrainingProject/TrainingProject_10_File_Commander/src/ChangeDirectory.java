import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ChangeDirectory {

    private ChangeDirectory() {
    }


    public static File changeDirectory(File pathDirectory, String targetDirectory) {
        Path temp1 = Paths.get(pathDirectory + "/" + targetDirectory);
        File newDirectory = temp1.normalize().toFile();

        if (newDirectory.isDirectory()) {
            return newDirectory;
        } else {
            System.out.println("Введён некорректный путь или директория отсутствует. Повторите попытку");
            return pathDirectory;
        }

    }
}
