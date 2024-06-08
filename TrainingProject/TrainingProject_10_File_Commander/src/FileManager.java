import java.io.File;
import java.util.Scanner;


public class FileManager {

    private static final String BOOT_ERROR_NO_PARAMETER = "Ошибка: Запуск программы должен осуществляться" +
            "с одним аргументом (абсолютный путь до директории),\nнапример: java Main --current-folder=/home.\n" +
            "Перезапустите программу с корректным аргументом.";


    private FileManager() {
    }


    public static void startProgram(String[] args) {
        File pathDirectory = checkedParametrProgram(args);
        System.out.println(pathDirectory);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] command = line.split(" ");
            if (line.equals("exit")) {
                break;
            } else if (command.length == 1 && command[0].equals("ls")) {
                ListDirectoryContents.printListContents(pathDirectory);
            } else if (command.length == 2 && command[0].equals("cd")) {
                pathDirectory = ChangeDirectory.changeDirectory(pathDirectory, command[1]);
                System.out.println(pathDirectory);
            } else if (command.length == 3 && command[0].equals("mv")) {
                MoveDirectoryOrFile.move(pathDirectory, command[1], command[2]);
            } else {
                System.out.println("Введена неккоректная команда");
            }
        }
        sc.close();
    }


    private static File checkedParametrProgram(String[] args) {
        if (args.length != 1) {
            System.out.println(BOOT_ERROR_NO_PARAMETER);
            System.exit(-1);
        }
        String[] parametr = args[0].split("=");
        if (parametr.length != 2) {
            System.out.println("некорректный путь до директории\n" + BOOT_ERROR_NO_PARAMETER);
            System.exit(-1);
        }
        if (!parametr[0].equals("--current-folder")) {
            System.out.println("некорректный параметр (слева от = )\n" + BOOT_ERROR_NO_PARAMETER);
            System.exit(-1);
        }
        File pathDirectory = new File(parametr[1]);
        if (!pathDirectory.isDirectory()) {
            System.out.println("директория не найдена\n" + BOOT_ERROR_NO_PARAMETER);
            System.exit(-1);
        }
        return pathDirectory;
    }

}
