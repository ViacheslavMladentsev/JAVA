import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class RecordSignatureTemplate {

    private final static String TEMPLATE_SIGNATURE = "/home/TrainingProject_8_IO_Stream/src/signature.txt";

    private static final ArrayList<ArrayList<String>> signature = new ArrayList<>();

    private static RecordSignatureTemplate instance = new RecordSignatureTemplate();


    private RecordSignatureTemplate() {
        try {
            readFile();
        } catch (Exception e) {
            System.exit(-1);
        }
    }


    public static RecordSignatureTemplate getInstance() {
        if (instance == null) {
            instance = new RecordSignatureTemplate();
        }
        return instance;
    }

    public static ArrayList<ArrayList<String>> getSignature() {
        return signature;
    }

    private void readFile() throws Exception {
        try {
            Scanner sc = new Scanner(new File(TEMPLATE_SIGNATURE));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] temp = line.split(", ");
                String[] hexCode = temp[1].toUpperCase().split(" ");
                if (temp.length == 2) {
                    ArrayList<String> hex = new ArrayList<>(Arrays.asList(hexCode));
                    hex.add(temp[0]);
                    signature.add(hex);
                } else {
                    sc.close();
                    throw new Exception("Ошибка: в файле с шаблонами сигнатур, присутствует некорректная запись - " + line);
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

}
