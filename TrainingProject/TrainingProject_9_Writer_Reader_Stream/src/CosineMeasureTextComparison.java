import java.io.IOException;

public class CosineMeasureTextComparison {


    private static final String BOOT_ERROR_NO_PARAMETER = "Ошибка: Запуск программы должен осуществляться с двумя аргументами (пути до файлов),\n" +
            "например: java Main 1.txt 2.txt.\nПерезапустите программу с корректным аргументом.";


    private CosineMeasureTextComparison() {
    }

    public static void cosineMeasureTextComparison(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(BOOT_ERROR_NO_PARAMETER);
        }

        CreateDictionary.getInstance();

        try {
            CreateDictionary.recordDictionary(args[0], args[1]);
            CreateDictionary.recordDictionaryInFile();

            CoincidenceVector vector1 = new CoincidenceVector();
            vector1.createCoincidenceVector(CreateDictionary.getSignature(), args[0]);

            CoincidenceVector vector2 = new CoincidenceVector();
            vector2.createCoincidenceVector(CreateDictionary.getSignature(), args[1]);

            System.out.printf("Similarity = %.2f\n", AnalyzerFiles.calculatingSimilarityCoefficient(vector1.getCoincidenceVector(), vector2.getCoincidenceVector()));


        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
