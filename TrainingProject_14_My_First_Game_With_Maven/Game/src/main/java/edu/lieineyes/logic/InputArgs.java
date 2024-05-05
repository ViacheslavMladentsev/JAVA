package edu.lieineyes.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Класс InputArgs работает на фундаменте библиотеке JCommander для валидации параметров указанных в момент запуска программы.
 * У программы есть 4 обязательных параметра --enemiesCount, --wallsCount, --size, --profile.
 * Каждый параметр принимает только 1 значение, и это значение проверяется через вызов класса ValidationStartGameParameters.
 * <p>
 * Геттеры возвращают уже корректные значения в том виде, который предусматривает логика программы:
 * --enemiesCount, --wallsCount, --size - возвращают целое положительное число;
 * --profile - возвращает строку.
 * <p>
 * Класс может выбросить исключение ParameterException, при проверках значений параметров с помощью класса ValidationStartGameParameters.
 */

@Parameters(separators = "=")
public class InputArgs {

    @Parameter(names = {"--enemiesCount"}, required = true, arity = 1, validateWith = ValidationStartGameParameters.class)
    private String enemiesCount;

    @Parameter(names = {"--wallsCount"}, required = true, arity = 1, validateWith = ValidationStartGameParameters.class)
    private String wallsCount;

    @Parameter(names = {"--size"}, required = true, arity = 1, validateWith = ValidationStartGameParameters.class)
    private String size;

    @Parameter(names = {"--profile"}, required = true, arity = 1, validateWith = ValidationStartGameParameters.class)
    private String profile;


    public int getEnemiesCount() {
        return Integer.parseInt(this.enemiesCount);
    }

    public int getWallsCount() {
        return Integer.parseInt(this.wallsCount);
    }

    public int getSize() {
        return Integer.parseInt(this.size);
    }

    public String getProfile() {
        return this.profile;
    }

}