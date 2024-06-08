import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class MoveDirectoryOrFile {

    public static void move(File pathDirectory, String nameCurrentFile, String targetPathFile) {
        Path pathCurrentFile = Paths.get(pathDirectory + "/" + nameCurrentFile);
        if (!Files.exists(pathCurrentFile)) {
            System.out.println("Ошибка: файл " + pathCurrentFile + " не существует. Повторите попытку.");
            return;
        }

        Path targetPath = Paths.get(targetPathFile);


        if (Files.isDirectory(targetPath)) {
            System.out.println("Целевой путь назначения не может быть просто директорией." +
                    "Повторите попытку, указав корректное назначения перемещения");

        } else {
            try {
                if (targetPath.getParent() == null) {
                    Files.move(pathCurrentFile, Paths.get(pathDirectory + "/" + targetPathFile), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.move(pathCurrentFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.out.println(e + "Повторите попытку, указав корректные данные для перемещения");
            }
        }

    }
}
