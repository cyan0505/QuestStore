package Controller;

import java.sql.SQLException;
import Model.*;
import View.*;

public class Controller {

    private DataManager dataManager;
    private View view;

    public Controller(){
        this.dataManager = new DataManager();
        this.view = new View();
    }

    public Boolean handleMenu(Integer number) throws SQLException {

        switch (number) {
            case 1:
                String table = view.getAnswerAsString("table: ");
                dataManager.insertRow(table);
                break;
            case 2:
                String table2 = view.getAnswerAsString("table: ");
                String column = view.getAnswerAsString("column: ");
                Integer id = view.getAnswerAsInt("id: ");
                dataManager.deleteRowUseId(table2, column, id);
                break;
            case 3:
                String table3 = view.getAnswerAsString("give table: ");
                dataManager.printGivenTable(table3);
                break;
            case 4:
//                handleShowNamePhoneNumUseEmail();
                break;
            case 5:
//                handleInsertRowShowApplicatUseCode();
                break;
            case 6:
//                handleUpdatePhoneNumberShowPhoneNumber();
                break;
            case 7:
//                handleDeleteRowUseEmail();
                break;
            case 8:
//                handleAdvanceSearch();
                break;
            case 0:
                dataManager.closeConnection();
                return false;
            default:
                break;
        }
        return true;
    }

    public boolean checkLogin(String login){
//        method from dao
        return false;
    }

    public boolean checkPassword(String password){
        return false;
    }






}
