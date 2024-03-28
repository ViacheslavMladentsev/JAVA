package printer.logic;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.nio.file.Files;
import java.nio.file.Path;

public class ValidationProgramParametrs implements IParameterValidator {

    private static final String TEMPLATE_MESSAGE_FOR_ERROR = "Количество параметров при запуске программы должно быть равно 2.\n" +
            "1ый параметр - на какой цвет для вывода заменить белые пиксели картинки;\n" +
            "2ой параметр - на какой цвет для вывода заменить чёрные пиксели картинки;\n" +
            "Путь к картинке константно прописан в коде;\n" +
            "Перезапустите программу с корректными параметрами!\n" +
            "Пример запуска: java Main --white=RED --black=GREEN\n" +
            "Программа работает только с двухцветными черно-белыми картинками с расширением .bmp";
    private static final String MESSAGE_FOR_ERROR_START_ARGUMENT = "Ошибка: отсутствует аргумент у параметра ";

    private static final String MESSAGE_FOR_ERROR_ARGUMENT = "Ошибка: некорректный параметр ";

    private static final String MESSAGE_FOR_ERROR_THIRD_ARGUMENT_EXISTS_FILE = "Ошибка: Файл не найден.\n" + TEMPLATE_MESSAGE_FOR_ERROR;

    private static final String MESSAGE_FOR_ERROR_THIRD_ARGUMENT_EXTANSION = "Ошибка: Расширение должно быть .bmp\n" + TEMPLATE_MESSAGE_FOR_ERROR;

    private static final String EXTENSION_FILE = "bmp";


    private static String nameArgument;
    private static String valueArgument;


    @Override
    public void validate(String name, String value) throws ParameterException {
        nameArgument = name;
        valueArgument = value;
        checkedLengthArgumnt();
        checkedArgumentByUpperCase();
    }


    private static void checkedLengthArgumnt() throws ParameterException {
        if (valueArgument.length() == 0) {
            throw new ParameterException(MESSAGE_FOR_ERROR_START_ARGUMENT +
                    nameArgument + ".\n" +
                    TEMPLATE_MESSAGE_FOR_ERROR);
        }
    }

    private static void checkedArgumentByUpperCase() throws ParameterException {
        for (int i = 0; i < valueArgument.length(); i++) {
            if (!Character.isUpperCase(valueArgument.charAt(i))) {
                throw new ParameterException(MESSAGE_FOR_ERROR_ARGUMENT +
                        nameArgument + ".\n" +
                        "Цвет должен быть написан в верхнем регистре.\n" +
                        TEMPLATE_MESSAGE_FOR_ERROR);
            }
        }
    }

    public static void checkedPathImage(final Path pathImage) throws ParameterException {
        if (!Files.exists(pathImage)) {
            throw new ParameterException(MESSAGE_FOR_ERROR_THIRD_ARGUMENT_EXISTS_FILE);
        } else {
            String[] fileName = pathImage.getFileName().toString().split("\\.");
            if (fileName.length != 2) {
                throw new ParameterException(MESSAGE_FOR_ERROR_THIRD_ARGUMENT_EXTANSION);
            } else if (!fileName[1].equals(EXTENSION_FILE)) {
                throw new ParameterException(MESSAGE_FOR_ERROR_THIRD_ARGUMENT_EXTANSION);
            }
        }
    }

}

