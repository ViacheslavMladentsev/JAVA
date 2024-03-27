import java.io.*;
import java.util.ArrayList;


public class CreateDictionary {

    private static final ArrayList<String> DICTIONARY = new ArrayList<>();

    private static CreateDictionary instance = new CreateDictionary();


    private CreateDictionary() {
    }


    public static CreateDictionary getInstance() {
        if (instance == null) {
            instance = new CreateDictionary();
        }
        return instance;
    }

    public static ArrayList<String> getSignature() {
        return DICTIONARY;
    }


    public static void recordDictionary(String pathFile1, String pathFile2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(pathFile1));
        BufferedReader reader2 = new BufferedReader(new FileReader(pathFile2));

        addUniqueStringInArrayList(reader1);
        reader1.close();

        addUniqueStringInArrayList(reader2);
        reader2.close();

        DICTIONARY.sort(null);
    }

    private static void addUniqueStringInArrayList(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] temp = line.split(" ");
            for (String s : temp) {
                if (!DICTIONARY.contains(s)) {
                    DICTIONARY.add(s);
                }
            }
        }
    }

    public static void recordDictionaryInFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/TrainingProject_9_Writer_Reader_Stream/src/dictionary.txt", true));
        for (int i = 0; i < DICTIONARY.size(); ++i) {
            if (i != DICTIONARY.size() - 1) {
                writer.write(DICTIONARY.get(i) + ", ");
            } else {
                writer.write(DICTIONARY.get(i));
            }
        }
        writer.close();
    }

}
