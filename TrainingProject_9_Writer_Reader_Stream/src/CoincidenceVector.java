import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class CoincidenceVector {

    private final ArrayList<Integer> coincidenceVector = new ArrayList<>();


    public ArrayList<Integer> getCoincidenceVector() {
        return coincidenceVector;
    }


    public void createCoincidenceVector(ArrayList<String> dictyonary, String pathFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathFile));
        ArrayList<String> readFile = new ArrayList<>();
        addStringInArrayList(reader, readFile);
        countRepetitionsLines(readFile, dictyonary);
        reader.close();
    }

    private static void addStringInArrayList(BufferedReader reader, ArrayList<String> readFile) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] temp = line.split(" ");
            readFile.addAll(Arrays.asList(temp));
        }
    }

    private void countRepetitionsLines(ArrayList<String> readFile, ArrayList<String> dictyonary) {
        for (String s : dictyonary) {
            int count = 0;
            for (String str : readFile) {
                if (s.equals(str)) {
                    ++count;
                }
            }
            coincidenceVector.add(count);
        }
    }

}
