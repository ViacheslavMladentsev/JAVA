import java.util.Scanner;

public class FileSignatureInterceptor {

    private FileSignatureInterceptor() {}


    public static void interceptorFileSignature() {
        RecordSignatureTemplate.getInstance();

        try {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("999")) {
                    break;
                } else {
                    RecordFileSignature fileSignature = new RecordFileSignature(line);
                    String formatName = SignatureAnalyzer.analyze(RecordSignatureTemplate.getSignature(), fileSignature.getFileSignature());
                    WriterInfo.write(formatName);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
