import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";

    public static void main(String[] args) {
        boolean done = false;

        while (!done) {
            System.out.println("\nMenu:");
            System.out.println("A – Add item");
            System.out.println("D – Delete item");
            System.out.println("I – Insert item");
            System.out.println("M – Move item");
            System.out.println("C – Clear list");
            System.out.println("V – View list");
            System.out.println("O – Open list file");
            System.out.println("S – Save list to file");
            System.out.println("Q – Quit");
            System.out.print("Enter choice: ");
            String choice = in.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    System.out.print("Enter item to add: ");
                    list.add(in.nextLine());
                    needsToBeSaved = true;
                    break;
                case "D":
                    viewList();
                    System.out.print("Enter index to delete: ");
                    int delIndex = Integer.parseInt(in.nextLine());
                    list.remove(delIndex);
                    needsToBeSaved = true;
                    break;
                case "I":
                    viewList();
                    System.out.print("Enter index to insert at: ");
                    int index = Integer.parseInt(in.nextLine());
                    System.out.print("Enter item: ");
                    list.add(index, in.nextLine());
                    needsToBeSaved = true;
                    break;
                case "M":
                    viewList();
                    System.out.print("Move from index: ");
                    int from = Integer.parseInt(in.nextLine());
                    System.out.print("To index: ");
                    int to = Integer.parseInt(in.nextLine());
                    String item = list.remove(from);
                    list.add(to, item);
                    needsToBeSaved = true;
                    break;
                case "C":
                    list.clear();
                    needsToBeSaved = true;
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    if (needsToBeSaved) {
                        System.out.print("You have unsaved changes. Save first? (Y/N): ");
                        if (in.nextLine().equalsIgnoreCase("Y")) {
                            saveList();
                        }
                    }
                    System.out.print("Enter filename to open: ");
                    currentFileName = in.nextLine();
                    loadList(currentFileName);
                    break;
                case "S":
                    saveList();
                    break;
                case "Q":
                    if (needsToBeSaved) {
                        System.out.print("Save before quitting? (Y/N): ");
                        if (in.nextLine().equalsIgnoreCase("Y")) {
                            saveList();
                        }
                    }
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewList() {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    private static void saveList() {
        try {
            if (currentFileName.isEmpty()) {
                System.out.print("Enter filename to save: ");
                currentFileName = in.nextLine();
            }
            Files.write(Paths.get("src", currentFileName), list, StandardOpenOption.CREATE);
            System.out.println("List saved to " + currentFileName);
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private static void loadList(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src", filename));
            list = new ArrayList<>(lines);
            System.out.println("List loaded from " + filename);
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}
