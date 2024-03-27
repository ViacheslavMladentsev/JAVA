import java.util.ArrayList;
import static java.lang.Math.*;


public class AnalyzerFiles {

    public static double calculatingSimilarityCoefficient(ArrayList<Integer> vector1, ArrayList<Integer> vector2) {
        return calculatingNumerator(vector1, vector2) / calculatingDenominator(vector1, vector2);
    }

    private static double calculatingNumerator(ArrayList<Integer> vector1, ArrayList<Integer> vector2) {
        double result = 0.0;
        for (int i = 0; i < vector1.size(); ++i) {
            result += vector1.get(i) * vector2.get(i);
        }
        return result;
    }

    private static double calculatingDenominator(ArrayList<Integer> vector1, ArrayList<Integer> vector2) {
        double resultVector1 = 0.0;
        double resultVector2 = 0.0;
        for (int i = 0; i < vector1.size(); ++i) {
            resultVector1 += pow(vector1.get(i), 2);
            resultVector2 += pow(vector2.get(i), 2);
        }
        return sqrt(resultVector1) * sqrt(resultVector2);
    }

}
