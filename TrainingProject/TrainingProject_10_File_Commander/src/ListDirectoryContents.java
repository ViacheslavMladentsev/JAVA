import java.io.File;


public class ListDirectoryContents {

    private ListDirectoryContents() {
    }


    public static void printListContents(File pathDirectory) {
        String[] content = pathDirectory.list();

        if (content != null) {
            for (String s : content) {
                File file = new File(pathDirectory + "/" + s);
                if (file.isDirectory()) {
                    System.out.println(s + " " + getDirectorySizeKiloBytes(file) + " kb");
                } else {
                    System.out.println(s + " " + getFileSizeKiloBytes(file));
                }
            }
        }
    }

    private static String getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024 + " kb";
    }

    private static double getDirectorySizeKiloBytes(File directory) {
        double length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += getDirectorySizeKiloBytes(file);
        }
        return length / 1024;
    }

}
