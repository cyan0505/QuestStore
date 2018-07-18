import Controller.Controller;
import View.View;

import java.sql.SQLException;

public class Main {

    private static View view;
    private static Controller controller;

    public static void main(String[] args) {
        view = new View();
        controller = new Controller();

        Boolean option = true;
        while (option) {
            view.printMenu();
            Integer answer = view.getAnswerAsInt("Choose option: ");
            try {
                option = controller.handleMenu(answer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
