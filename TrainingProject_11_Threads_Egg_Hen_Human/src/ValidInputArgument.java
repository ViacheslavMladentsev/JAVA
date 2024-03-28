public class ValidInputArgument {

    private static final String MESSAGE_FOR_ERROR_START_ARGUMENT = "Ошибка:" +
            "количество аргументов при запуске программы должно быть равно 1. Пример: java Main --count=50";

    private static final String MESSAGE_FOR_INCORRECT_ARGIMENT = "Ошибка: аргумет должен содержать --count=<number>," +
            "где number - это целое число повторений больше 0";

    private static final String DELIMETR_IN_PARAMETR = "=";

    private static final String NAME_ARGUMENT = "--count";

    private static final int CODE_FOR_FORCED_TERMINATION_PROGRAM = -1;

    private static final int COUNT_ARGUMENTS = 1;

    private static final int MINIMUM_VALUE = 1;


    private ValidInputArgument() {
    }


    public static int checkedParametrs(String[] args) {
        if (args.length != COUNT_ARGUMENTS) {
            System.out.println(MESSAGE_FOR_ERROR_START_ARGUMENT);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }

        String[] temp = args[0].split(DELIMETR_IN_PARAMETR);

        if (!temp[0].equals(NAME_ARGUMENT) || Integer.parseInt(temp[1]) < MINIMUM_VALUE) {
            System.out.println(MESSAGE_FOR_INCORRECT_ARGIMENT);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }

        return Integer.parseInt(temp[1]);
    }

}
