import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class View {

    private Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void printMenu() {
        List<String> menu = Arrays.asList("Insert row to table.", "delete row", "select * from table");
        System.out.printf("%nWhat would you like to do:%n");
        int index = 1;
        for (String menuItem : menu) {
            System.out.printf("    (%d) %s%n", index, menuItem);
            index++;
        }
        System.out.println("    (0) Exit");
    }

    public String getAnswerAsString(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    public Integer getAnswerAsInt(String question) {
        Integer input = null;
        while (input == null) {
            System.out.print(question);
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.err.println("Type integer!");
            }
        }
        scanner.nextLine();
        return input;
    }
}
