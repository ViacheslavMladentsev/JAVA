import java.io.FileOutputStream;
import java.io.IOException;

public class WriterInfo {

    private static final String FILE_RESULT = "/home/TrainingProject_8_IO_Stream/src/result.txt";


    private WriterInfo() {
    }


    public static void write(String formatName) {
        if (formatName != null) {
            try {
                FileOutputStream ois = new FileOutputStream(FILE_RESULT, true);
                ois.write((formatName + "\n").getBytes());
                System.out.println("PROCESSED");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("UNDEFINED");
        }
    }

}
