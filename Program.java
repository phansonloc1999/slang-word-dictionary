import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Program
 */
public class Program {
    private static Hashtable<String, String[]> my_dict;

    private static void writeToHistory(String slang) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("Data/history.txt", true));
            out.write(slang + "\n");
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void showHistory() {
        try {
            Scanner scanner = new Scanner(new File("Data/history.txt"));
            System.out.println("=======================Lich su tim kiem Slang=======================");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void searchSlang() {
        System.out.println("=======================Tim kiem slang=======================");
        System.out.print("Nhap tu khoa slang can tim: ");
        Scanner scanner = new Scanner(System.in);
        String slang = scanner.nextLine();
        String[] meanings = my_dict.get(slang);
        if (meanings != null) {
            System.out.println("Y nghia cua tu " + slang + " la :");
            for (String meaning : meanings) {
                System.out.println(meaning);
            }
        }
        writeToHistory(slang);
    }

    private static void searchKeywordInMeaning() {
        System.out.println("=======================Tim kiem keyword=======================");
        System.out.print("Nhap tu khoa can tim kiem: ");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.nextLine();
        Enumeration<String> e = my_dict.keys();
        while (e.hasMoreElements()) {
            String slang = e.nextElement();
            String[] meanings = my_dict.get(slang);
            for (int i = 0; i < meanings.length; i++) {
                if (meanings[i].contains(keyword)) {
                    System.out.println("Slang: " + slang + " Meaning: " + meanings[i]);
                }
            }
        }
    }

    private static void readSlangsFromFile(String filePath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath), "UTF-8");
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

    private static void addSlang() {
        System.out.println("=======================Them slang=======================");
        System.out.print("Nhap slang muon them: ");
        Scanner scanner = new Scanner(System.in);
        String slang = scanner.nextLine();
        System.out.print("So luong nghia cua slang: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        String[] meanings = new String[num];
        for (int i = 0; i < num; i++) {
            System.out.print("Nhap nghia thu " + (i + 1) + ": ");
            meanings[i] = scanner.nextLine();
        }
        my_dict.put(slang, meanings);
        writeSlangsToFile();
    }

    private static void writeSlangsToFile() {
        try {
            FileWriter fWriter = new FileWriter(new File("Data/slang.txt"));
            Enumeration<String> e = my_dict.keys();
            fWriter.write("Slang`Meaning\n");
            while (e.hasMoreElements()) {
                String slang = e.nextElement();
                fWriter.write(slang + "`");
                String[] meanings = my_dict.get(slang);
                for (int i = 0; i < meanings.length; i++) {
                    fWriter.write(meanings[i] + "\n");
                }

            }
            fWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void editSlang() {
        System.out.println("=======================Chinh sua slang=======================");
        System.out.print("Nhap slang muon chinh sua: ");
        Scanner scanner = new Scanner(System.in);
        String slang = scanner.nextLine();
        if (my_dict.get(slang) != null) {
            System.out.println("Slang: " + slang);
            String[] meanings = my_dict.get(slang);
            for (int i = 0; i < meanings.length; i++) {
                System.out.println("Nghia thu " + (i + 1) + " :" + meanings[i]);
            }
            System.out.println("==============================================");

            System.out.print("Nhap so luong nghia cua slang: ");
            int numOfMeanings = scanner.nextInt();
            scanner.nextLine();

            String[] newMeanings = new String[numOfMeanings];
            for (int i = 0; i < numOfMeanings; i++) {
                System.out.print("Nhap nghia thu " + (i + 1) + " cua slang: ");
                newMeanings[i] = scanner.nextLine();
            }
            my_dict.put(slang, newMeanings);
            return;
        }

        System.out.println("Khong tim thay slang da nhap!");
    }

    private static void deleteSlang() {
        System.out.println("=======================Chinh sua slang=======================");
        System.out.print("Nhap slang muon xoa: ");
        Scanner scanner = new Scanner(System.in);
        String slangToDelete = scanner.nextLine();
        if (my_dict.get(slangToDelete) != null) {
            do {
                System.out.print("Ban co xac nhan muon xoa slang " + slangToDelete + " khong?(Y/N): ");
                String confirmation = scanner.nextLine();
                if (confirmation.equals("Y") || confirmation.equals("y")) {
                    my_dict.remove(slangToDelete);
                    System.out.println("Da xoa slang " + slangToDelete);
                    return;
                } else if (confirmation.equals("N") || confirmation.equals("n")) {
                    System.out.println("Khong xoa slang " + slangToDelete);
                    return;
                }
            } while (true);
        }

        System.out.println("Khong tim thay slang da nhap!");
    }

    public static void resetOriginalSlangs() {
        readSlangsFromFile("Data/slang_original.txt");
    }

    public static void main(String[] args) {
        my_dict = new Hashtable<String, String[]>();
        readSlangsFromFile("Data/slang.txt");
        searchSlang();
        // searchKeywordInMeaning();
        // showHistory();
        // addSlang();
        // editSlang();
        // deleteSlang();
        // resetOriginalSlangs();
        // writeSlangsToFile();
    }
}