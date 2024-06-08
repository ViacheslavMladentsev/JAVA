package edu.lieineyes.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс ValidationPropertiesFile осуществляет проверку существования файлов application-dev.properties и
 * application-production.properties, в зависимости от заданного параметра --profile при запуске программы.
 * Так же проверяет содержимое этих файлов на корректность (наличие обязательных данных и соответствующих значений).
 * <p>
 * checkedOpenPropertiesFile - метод проверяет существование файла и можно ли его открыть.
 * Если всё ок, то загружаем его содержимое в переменную properties. Если файл не корректный или отсутствует, выбрасывается исключение;
 * loadProperties - метод пытается загрузить содержимое файла в переменную properties. Может выкинуть исключение.
 * checkingExistenceRequiredSettings - метод проверяет присутствие обязательных параметров в файле, а также их значения на корректность.
 * <p>
 * Класс может выбросить исключения FileNotFoundException, RuntimeException.
 */

public class ValidationPropertiesFile {

    private static final String MESSAGE_ERROR_RECOMMENDATION_FOR_PROPERTIES_FILE =
            "\n\nФайл должен содержать набор обязательных параметров:\n" +
                    "enemy.char = <символ для отображения врага на игровом поле>\n" +
                    "player.char = <символ для отображения игрока на игровом поле>\n" +
                    "wall.char = <символ для отображения препятствия на игровом поле>\n" +
                    "goal.char = <символ для отображения клетки на игровом поле, куда необходимо дойти игроку>\n" +
                    "empty.char = <символ для отображения пустой клетки на игровом поле>\n" +
                    "enemy.color = <цвет клетки занятой врагом>\n" +
                    "player.color = <цвет клетки занятой игроком>\n" +
                    "wall.color = <цвет клетки занятой препятствием>\n" +
                    "goal.color = <цвет клетки где расположен финиш>\n" +
                    "empty.color = <цвет не занятой клетки>\n\n" +
                    "Измените файл с настройками и перезапустите программу";

    private static final String PATH_FILE_PROPERTIES_PRODUCTION = "target/classes/application-production.properties";

    private static final String PATH_FILE_PROPERTIES_DEV = "target/classes/application-dev.properties";

    private static final String FILE_PROPERTIES_PRODUCTION = "application-production.properties";

    private static final String FILE_PROPERTIES_DEV = "application-dev.properties";

    private static final String MODE_USER = "production";

    private static final String MODE_DEVELOPMENT = "dev";

    private static final String MESSAGE_ERROR_EXISTS_PARAMETERS_PROPERTIES = "Ошибка: отсутствует обязательный параметр ";

    private static final String MESSAGE_ERROR_INCORRECT_ARGUMENTS_PROPERTIES = "Ошибка: некорректно задана настройка параметра ";

    private static final String MESSAGE_FOR_WHERE_FILE_ERROR = " в файле ";


    private static final String[] LIST_NAME_CHAR_PARAMETERS_PROPERTIES = {"enemy.char", "player.char", "wall.char",
            "goal.char", "empty.char", "enemy.color", "player.color", "wall.color", "goal.color", "empty.color"};


    private static Properties properties;

    private static FileInputStream in;


    private ValidationPropertiesFile() {

    }

    public static void validationProperties(final InputArgs arguments, final Properties properties) throws FileNotFoundException, RuntimeException {
        ValidationPropertiesFile.properties = properties;
        if (arguments.getProfile().equals(MODE_USER)) {
            checkingPropertiesDependingMode(PATH_FILE_PROPERTIES_PRODUCTION, FILE_PROPERTIES_PRODUCTION);
        } else if (arguments.getProfile().equals(MODE_DEVELOPMENT)) {
            checkingPropertiesDependingMode(PATH_FILE_PROPERTIES_DEV, FILE_PROPERTIES_DEV);
        }
    }

    private static void checkingPropertiesDependingMode(final String pathFile, final String fileName) throws FileNotFoundException, RuntimeException {
        checkedOpenPropertiesFile(pathFile);
        loadProperties();
        checkingExistenceRequiredSettings(fileName);
    }

    private static void checkedOpenPropertiesFile(final String pathPropertiesFile) throws FileNotFoundException, RuntimeException {
        try {
            in = new FileInputStream(pathPropertiesFile);
            loadProperties();
        } catch (FileNotFoundException | RuntimeException e) {
            throw new FileNotFoundException(String.valueOf(e));
        }
    }

    private static void loadProperties() throws RuntimeException {
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkingExistenceRequiredSettings(final String fileNameProperties) throws RuntimeException {
        for (String str : LIST_NAME_CHAR_PARAMETERS_PROPERTIES) {
            if (properties.getProperty(str) == null) {
                throw new RuntimeException(MESSAGE_ERROR_EXISTS_PARAMETERS_PROPERTIES +
                        str +
                        MESSAGE_FOR_WHERE_FILE_ERROR +
                        fileNameProperties +
                        MESSAGE_ERROR_RECOMMENDATION_FOR_PROPERTIES_FILE);
            }
            if (str.split("\\.")[1].equals("char")) {
                if (properties.getProperty(str).length() > 1) {
                    throw new RuntimeException(MESSAGE_ERROR_INCORRECT_ARGUMENTS_PROPERTIES +
                            str +
                            MESSAGE_FOR_WHERE_FILE_ERROR +
                            fileNameProperties +
                            MESSAGE_ERROR_RECOMMENDATION_FOR_PROPERTIES_FILE);
                }
            }
        }
    }

}