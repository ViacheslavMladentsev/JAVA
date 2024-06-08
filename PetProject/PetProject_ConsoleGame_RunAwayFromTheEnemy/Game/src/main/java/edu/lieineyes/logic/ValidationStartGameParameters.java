package edu.lieineyes.logic;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * В классе ValidationStartGameParameters происходит валидация параметров и их значений, задаваемых при запуске программы.
 * Класс ValidationStartGameParameters унаследован от интерфейса IParameterValidator из библиотеки JCommander.
 * Определён метод validate для корректной обработки параметров через аннотацию в классе InputArgs.
 * <p>
 * Метод checkedNumeric проверяет валидность числового значения у параметров --size, --wallsCount, --enemiesCount
 * <p>
 * Метод checkedProfile проверяет валидность значения параметра --profile, которое может принимать только два значения production или dev
 * <p>
 * Класс может выбросить исключение ParameterException.
 */

public class ValidationStartGameParameters implements IParameterValidator {

    private static final String PARAMETER_SIZE = "--size";
    private static final String PARAMETER_WALLS_COUNT = "--wallsCount";
    private static final String PARAMETER_ENEMIES_COUNT = "--enemiesCount";
    private static final String PARAMETER_PROFILE = "--profile";
    private static final String ARGUMENT_PROFILE_PRODUCTION = "production";
    private static final String ARGUMENT_PROFILE_DEV = "dev";


    private static final String MESSAGE_ERROR_ARGUMENT = "Некорректный аргумент у параметра ";
    private static final String MESSAGE_ERROR_RECOMMENDATION_FOR_START_PROGRAM =
            "\n\nИгра должна запускаться с 4 обязательными параметрами в произвольном порядке:\n" +
                    "--size=<целое положительное число в диапазоне от 2 до 100 включительно> - размер стороны игрового поля\n" +
                    "--wallsCount=<целое положительное число> - количество препятствий на игровой карте\n" +
                    "--enemiesCount=<целое положительное число> - количество врагов, преследующих игрока\n" +
                    "--profile=<строка> - может принимать 2 значения\n" +
                    "\t--profile=<production> - запуск игры в режиме пользователя\n" +
                    "\t--profile=<dev> - запуск игры в режиме разработчика\n" +
                    "Пример запуска игры: java -jar game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production\n\n" +
                    "Перезапустите программу с корректными параметрами";


    private static String nameArgument;

    private static String valueArgument;


    @Override
    public void validate(String name, String value) throws ParameterException {
        nameArgument = name;
        valueArgument = value;
        if (name.equals(PARAMETER_SIZE) || name.equals(PARAMETER_WALLS_COUNT) || name.equals(PARAMETER_ENEMIES_COUNT)) {
            checkedNumeric();
        } else if (name.equals(PARAMETER_PROFILE)) {
            checkedProfile();
        }
    }


    private void checkedNumeric() throws NumberFormatException {
        try {
            Integer.parseInt(valueArgument);
        } catch (NumberFormatException e) {
            throw new ParameterException(MESSAGE_ERROR_ARGUMENT +
                    nameArgument + MESSAGE_ERROR_RECOMMENDATION_FOR_START_PROGRAM);
        }
    }

    private void checkedProfile() throws ParameterException {
        if (!(valueArgument.equals(ARGUMENT_PROFILE_PRODUCTION) || valueArgument.equals(ARGUMENT_PROFILE_DEV))) {
            throw new ParameterException(MESSAGE_ERROR_ARGUMENT +
                    nameArgument + MESSAGE_ERROR_RECOMMENDATION_FOR_START_PROGRAM);
        }
    }

}