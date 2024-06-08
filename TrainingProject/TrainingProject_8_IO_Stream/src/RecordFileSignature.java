import java.io.FileInputStream;
import java.util.ArrayList;

public class RecordFileSignature {

    private final ArrayList<String> fileSignature = new ArrayList<>();

    private final String filePath;


    public RecordFileSignature(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> getFileSignature() {
        byte[] temp = new byte[8];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            fis.read(temp, 0, 8);
            for (int i = 0; i < temp.length; ++i) {
                fileSignature.add(String.format("%02x".toUpperCase(), temp[i]));
            }
            fis.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
        return fileSignature;
    }

}
