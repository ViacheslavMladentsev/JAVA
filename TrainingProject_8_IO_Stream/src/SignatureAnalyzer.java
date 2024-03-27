import java.util.ArrayList;

public class SignatureAnalyzer {


    private SignatureAnalyzer() {}

    public static String analyze(ArrayList<ArrayList<String>> templateSignature, ArrayList<String> fileSignature) {
        for (ArrayList<String> s : templateSignature) {
            boolean isExit = true;
            for (int i = 0; i < s.size() - 2; ++i) {
                if (!s.get(i).equals(fileSignature.get(i))) {
                    isExit = false;
                    break;
                }
            }
            if (isExit) {
                return s.get(s.size() - 1);
            }
        }
        return null;
    }

}
