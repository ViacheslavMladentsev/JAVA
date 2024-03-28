public class ValidationProgramParametrs {

    private static final String MESSAGE_FOR_ERROR_START_ARGUMENT = "Ошибка: количество аргументов," +
            " при запуске программы должно быть равно 1.\nПример: java Main --threadsCount=3.\n" +
            "Перезапустите программу с корректным набором параметров.";

    private static final String MESSAGE_FOR_INCORRECT_FORMAT_ARGUMENT = "Ошибка: должен содержать " +
            "--threadsCount=<number>,\nгде number - это целое число больше 0.\n" +
            "Перезапустите программу с корректным набором параметров.";

    private static final String DELIMETR_IN_PARAMETR = "=";

    private static final String NAME_ARGUMENT = "--threadsCount";

    private static final int MIN_COUNT_THREADS = 1;


    private static String[] arrayParametrs = null;

    private static String[] parameter = null;

    private static int countThread = 0;


    public static int validation(final String[] args) {
        arrayParametrs = args;
        checkedCountParametrs();
        parameter = args[0].split(DELIMETR_IN_PARAMETR);
        checkedParameter();
        return countThread;
    }

    private static void checkedCountParametrs() {
        if (arrayParametrs.length != 1) {
            System.out.println(MESSAGE_FOR_ERROR_START_ARGUMENT);
            System.exit(-1);
        }
    }

    private static void checkedParameter() {
        try {
            countThread = Integer.parseInt(parameter[1]);
        } catch (Exception e) {
            System.out.println(MESSAGE_FOR_INCORRECT_FORMAT_ARGUMENT);
            System.exit(-1);
        }

        if (!parameter[0].equals(NAME_ARGUMENT) || !(countThread >= MIN_COUNT_THREADS)) {
            System.out.println(MESSAGE_FOR_INCORRECT_FORMAT_ARGUMENT);
            System.exit(-1);
        }
    }

}
