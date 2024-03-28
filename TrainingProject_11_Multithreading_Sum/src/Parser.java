public class Parser {

    private static final String MESSAGE_FOR_ERROR_START_ARGUMENT = "Ошибка: количество аргументов," +
            " при запуске программы должно быть равно 2.\nПример: java Main --arraySize=13 --threadsCount=3.\n" +
            "Перезапустите программу с корректным набором параметров.";
    private static final String MESSAGE_FOR_INCORRECT_FIRST_ARGUMENT = "Ошибка: 1ый аргумет должен содержать " +
            "--arraySize=<number>,\nгде number - это целое число в диапозоне от 0 до 2,000,000.\n" +
            "Перезапустите программу с корректным набором параметров.";

    private static final String MESSAGE_FOR_INCORRECT_SECOND_ARGUMENT = "Ошибка: 2ой аргумет должен содержать " +
            "--threadsCount=<number>,\nгде number - это целое число больше 1.\n" +
            "Перезапустите программу с корректным набором параметров.";

    private static final String MESSAGE_ERROR_COMPARISON_PARAMETRS = "Ошибка: количество потоков (--threadsCount)" +
            " не может быть больше количества элементов в массиве (--arraySize).\n" +
            "Перезапустите программу с корректным набором параметров.";


    private static final String NAME_FIRST_ARGUMENT = "--arraySize";
    private static final String NAME_SECOND_ARGUMENT = "--threadsCount";

    private static final String DELIMETR_IN_PARAMETR = "=";

    private static final int COUNT_ARGUMENTS = 2;

    private static final int MAX_COUNT_ARRAY = 2000000;

    private static final int MIN_COUNT_ARRAY = 2;

    private static final int MIN_COUNT_THREAD = 2;

    private static final int CODE_FOR_FORCED_TERMINATION_PROGRAM = -1;



    private static int sizetArray = 0;
    private static int countThread = 0;


    private static String[] arrayParametrs = null;


    public Parser(final String[] args) {
        arrayParametrs = args;
        parse();
    }


    private static void parse() {
        if (arrayParametrs.length != COUNT_ARGUMENTS) {
            System.out.println(MESSAGE_FOR_ERROR_START_ARGUMENT);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }

        final String[] firstArguments = arrayParametrs[0].split(DELIMETR_IN_PARAMETR);
        final String[] secondArguments = arrayParametrs[1].split(DELIMETR_IN_PARAMETR);

        checkedFirstParametr(firstArguments);
        checkedSecondParametr(secondArguments);

        validationParametrs();
    }

    private static void checkedFirstParametr(final String[] argument) {
        sizetArray = checkedTypeConversionStringToInt(argument, MESSAGE_FOR_INCORRECT_FIRST_ARGUMENT);

        if (!argument[0].equals(NAME_FIRST_ARGUMENT) || !(sizetArray >= MIN_COUNT_ARRAY && sizetArray <= MAX_COUNT_ARRAY)) {
            System.out.println(MESSAGE_FOR_INCORRECT_FIRST_ARGUMENT);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }
    }

    private static void checkedSecondParametr(final String[] argument) {
        countThread = checkedTypeConversionStringToInt(argument, MESSAGE_FOR_INCORRECT_SECOND_ARGUMENT);

        if (!argument[0].equals(NAME_SECOND_ARGUMENT) || !(countThread >= MIN_COUNT_THREAD)) {
            System.out.println(MESSAGE_FOR_INCORRECT_SECOND_ARGUMENT);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }
    }

    private static int checkedTypeConversionStringToInt(String[] argument, String messageError) {
        int result = 0;
        try {
            result = Integer.parseInt(argument[1]);
        } catch (Exception e) {
            System.out.println(messageError);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }
        return result;
    }

    private static void validationParametrs() {
        if (sizetArray < countThread) {
            System.out.println(MESSAGE_ERROR_COMPARISON_PARAMETRS);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }
    }


    public int getSizeArray() {
        return sizetArray;
    }

    public int getCountThread() {
        return countThread;
    }

}
