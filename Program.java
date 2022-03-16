import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Program
 */
public class Program {

    public static void main(String[] args) {
        Hashtable<String, String[]> my_dict = new Hashtable<String, String[]>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("Data/slang,txt"), "UTF-8");
            scanner.nextLine(); // Skip first line
            String[] tokens = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] oldTokens = tokens;
                tokens = line.split("`");
                if (tokens.length == 2) {
                    System.out.println(tokens[0] + ": " + tokens[1]);
                    my_dict.put(tokens[0], new String[] { tokens[1] });
                } else {
                    my_dict.put(oldTokens[0], new String[] { oldTokens[1], tokens[0] });
                    System.out.println(tokens[0]);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}