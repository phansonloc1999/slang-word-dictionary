import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
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

    private static void showSearchHistory() {
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
        } else
            System.out.println("Khong tim thay slang da nhap!");
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
        writeSlangsToFile();
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
        writeSlangsToFile();
    }

    public static void resetOriginalSlangs() {
        readSlangsFromFile("Data/slang_original.txt");
    }

    public static String randomizeSlang() {
        Random generator = new Random();
        Enumeration<String> keyEnums = my_dict.keys();
        int randNum = generator.nextInt(my_dict.size());
        String[] keys = new String[my_dict.size()];
        int i = 0;
        String slang = null;
        do {
            slang = keyEnums.nextElement();
            i++;
        } while (keyEnums.hasMoreElements() && i < randNum);
        return slang;
    }

    private static void guessMeaning() {
        System.out.println("=======================Do vui chon nghia dung cho slang=======================");
        String slang = randomizeSlang();
        String[] answers = new String[4];
        Random random = new Random();
        int randNum = random.nextInt(4);
        answers[randNum] = my_dict.get(slang)[0];
        for (int i = 0; i < answers.length; i++) {
            if (i != randNum)
                answers[i] = my_dict.get(randomizeSlang())[0];
        }
        System.out.println("Nghia cua tu slang " + slang + " la gi ?");
        for (int i = 0; i < answers.length; i++) {
            System.out.println((i + 1) + ". " + answers[i]);
        }
        System.out.print("Nhap cau tra loi cua ban: ");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();
        scanner.nextLine();

        if (answer == randNum + 1) {
            System.out.println("Chuc mung ban tra loi dung!");
        } else {
            System.out.println("Rat tiec ban tra loi sai! Dap an dung la " + (randNum + 1));
        }
    }

    private static void guessSlang() {
        System.out.println("=======================Do vui chon slang dung voi nghia=======================");
        String slang = randomizeSlang();
        String[] answers = new String[4];
        Random random = new Random();
        int randNum = random.nextInt(4);
        answers[randNum] = slang;
        for (int i = 0; i < answers.length; i++) {
            if (i != randNum)
                answers[i] = randomizeSlang();
        }
        System.out.println("Slang nao co nghia nhu sau: " + my_dict.get(slang)[0] + " ?");
        for (int i = 0; i < answers.length; i++) {
            System.out.println((i + 1) + ". " + answers[i]);
        }
        System.out.print("Nhap cau tra loi cua ban: ");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();
        scanner.nextLine();

        if (answer == randNum + 1) {
            System.out.println("Chuc mung ban tra loi dung!");
        } else {
            System.out.println("Rat tiec ban tra loi sai! Dap an dung la " + (randNum + 1));
        }
    }

    private static void showMenu() {
        int choice;
        do {
            System.out.println("=======================Menu=======================");
            System.out.println("1. Tim kiem slang");
            System.out.println("2. Tim kiem tu khoa trong nghia cua slang");
            System.out.println("3. Hien thi lich su tim kiem slang");
            System.out.println("4. Them slang");
            System.out.println("5. Chinh sua slang");
            System.out.println("6. Xoa slang");
            System.out.println("7. Reset danh sach slang ban dau");
            System.out.println("8. Ghi slang ra file");
            System.out.println("9. Hien thi slang ngau nhien");
            System.out.println("10. Doan nghia cua slang");
            System.out.println("11. Doan slang dua theo nghia");
            System.out.println("-1. Thoat");
            System.out.print("Nhap lua chon cua ban: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchSlang();
                    break;

                case 2:
                    searchKeywordInMeaning();
                    break;

                case 3:
                    showSearchHistory();
                    break;

                case 4:
                    addSlang();
                    break;

                case 5:
                    editSlang();
                    break;

                case 6:
                    deleteSlang();
                    break;

                case 7:
                    resetOriginalSlangs();
                    break;

                case 8:
                    writeSlangsToFile();
                    break;

                case 9:
                    System.out.println("Tu slang ngau nhien la: " + randomizeSlang());
                    break;

                case 10:
                    guessMeaning();
                    break;

                case 11:
                    guessSlang();
                    break;

                case -1:
                    return;
            }
        } while (choice != -1);

    }

    public static void main(String[] args) {
        my_dict = new Hashtable<String, String[]>();
        readSlangsFromFile("Data/slang.txt");
        showMenu();
    }
}