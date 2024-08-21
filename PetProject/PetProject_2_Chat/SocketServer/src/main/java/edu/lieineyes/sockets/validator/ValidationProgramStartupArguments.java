package edu.lieineyes.sockets.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ValidationProgramStartupArguments implements IParameterValidator  {

    private static final String TEMPLATE_MESSAGE_FOR_ERROR =
            "Количество параметров при запуске программы должно быть равно 1.\n" +
            "В качестве значения параметра --port задаётся порт, по которому смогут подключаться клиенты.\n" +
            "Перезапустите программу с корректным параметром!\n" +
            "Пример запуска: java -jar target/socket-server.jar --port=8081\n";

    private static final String MESSAGE_FOR_ERROR_START_ARGUMENT = "Ошибка: отсутствует аргумент у параметра ";


    @Override
    public void validate(String name, String value) throws ParameterException {
        if (value.isEmpty()) {
            throw new ParameterException(MESSAGE_FOR_ERROR_START_ARGUMENT +
                    name + ".\n" +
                    TEMPLATE_MESSAGE_FOR_ERROR);
        }
    }

}
