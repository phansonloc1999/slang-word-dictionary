import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Program
 */
public class Program {
    private static Hashtable<String, String[]> my_dict;

    private static void searchSlang() {
        System.out.println("=======================Tim kiem slang=======================");
        System.out.print("Nhap tu khoa slang can tim: ");
        Scanner scanner = new Scanner(System.in);
        String slang = scanner.nextLine();
        String[] meanings = my_dict.get(slang);
        if (meanings.length > 0) {
            System.out.println("Y nghia cua tu " + slang + " la :");
            for (String meaning : meanings) {
                System.out.println(meaning);
            }
        }
    }

    private static void readSlangsFromFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("Data/slang,txt"), "UTF-8");
            scanner.nextLine(); // Skip first line
            String[] tokens = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] oldTokens = tokens;
                tokens = line.split("`");
                if (tokens.length == 2) {
                    my_dict.put(tokens[0], new String[] { tokens[1] });
                } else {
                    my_dict.put(oldTokens[0], new String[] { oldTokens[1], tokens[0] });
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scanner.close();
    }

    public static void main(String[] args) {
        my_dict = new Hashtable<String, String[]>();
        readSlangsFromFile();
        searchSlang();
    }
}