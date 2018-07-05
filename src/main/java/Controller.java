import java.sql.SQLException;

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

    private void handleAdvanceSearch() throws SQLException {
        String answer = view.getAnswerAsString("What are you searching for: ");
        dataManager.advanceSearchApplicants(answer);
        dataManager.advanceSearchMentors(answer);
    }

//    private void handleDeleteRowUseEmail() throws SQLException {
//        String answer = view.getAnswerAsString("Give email of applicant you want do delete: ");
//        dataManager.deleteRowUseEmail(answer);
//    }

    private void handleUpdatePhoneNumberShowPhoneNumber() throws SQLException {
        String firstName = view.getAnswerAsString("Give first name of applicant: ");
        String lastName = view.getAnswerAsString("Give last name of applicant: ");
        String phoneNumber = view.getAnswerAsString("Give new phoneNumber of applicant: ");

        dataManager.updatePhoneNumberUseName(firstName, lastName, phoneNumber);

        firstName = view.getAnswerAsString("Give first name of applicant: ");
        lastName = view.getAnswerAsString("Give last name of applicant: ");

        dataManager.selectPhoneNumberUseName(firstName, lastName);
    }

//        private void handleInsertRowShowApplicatUseCode() throws SQLException {
//        String firstName = view.getAnswerAsString("Give first name of applicant: ");
//        String lastName = view.getAnswerAsString("Give last name of applicant: ");
//        String phoneNumber = view.getAnswerAsString("Give phoneNumber of applicant: ");
//        String email = view.getAnswerAsString("Give email of applicant: ");
//        Integer appCode = view.getAnswerAsInt("Give application code of applicant: ");
//
//        dataManager.insertRow(firstName, lastName, phoneNumber, email, appCode);
//
//        appCode = view.getAnswerAsInt("Give application code of applicant you want to see: ");
//
//        dataManager.selectApplicantUseCode(appCode);
//    }

    private void handleShowNamePhoneNumUseEmail() throws SQLException {
        String answer;
        answer = view.getAnswerAsString("Choose email of applicant to see his/her phone number: ");
        dataManager.selectFullNamePhoneNumberUseEmail(answer);
    }

    private void handleShowNamePhoneNumUseName() throws SQLException {
        String answer = view.getAnswerAsString("Choose first name of applicant to see his/her phone number: ");
        dataManager.selectFullNamePhoneNumberUseName(answer);
    }

    private void handleMentorsFromCity() throws SQLException {
        String answer = view.getAnswerAsString("Choose city to see mentors from it: ");
        dataManager.selectMentorsFromCIty(answer);
    }
}
