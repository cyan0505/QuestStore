import java.sql.*;

public class DataManager {
    private View view;
    private Connection c;
    private Statement stmt;

    DataManager() {
        view = new View();
        loadDatabase();
    }

    private void loadDatabase() {
        try {
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "postgres", "srodkowy13"); // set user and password
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void insertRow(String table) throws SQLException {
        StringBuilder colNames = new StringBuilder();
        StringBuilder colValues = new StringBuilder();

        stmt = c.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + table + ";" );
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        for (int i = 2; i <= columnsNumber; i++) {
            colNames.append(rsmd.getColumnName(i));
            if (i< columnsNumber){
                colNames.append(", ");
            }
        }

        for (int i=1; i<columnsNumber ; i++){
            String value = view.getAnswerAsString("Col " + rsmd.getColumnName(i+1) + " value: ");
            colValues.append("'" + value + "'");
            if (i < columnsNumber - 1) {
                colValues.append(", ");
            }
        }

        String sql = "INSERT INTO " + table + " VALUES (DEFAULT, " + colValues + ");";
        stmt.executeUpdate(sql);
        System.out.println("Row added");
        System.out.println();
        stmt.close();
    }

    public void deleteRowUseId(String table, String column,  Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = "DELETE FROM " + table + "WHERE " + column + "LIKE '%" + id + "%';";
        stmt.executeUpdate(sql);
        System.out.println("Record deleted");
    }

    public void printGivenTable(String table) throws SQLException {
        stmt = c.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + table + ";" );
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println();
        }
        stmt.close();
    }

    public void selectMentorsFromCIty(String city) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nick_name FROM mentors WHERE city LIKE '%" + city + "%'");
        Integer id = 1;
        while (rs.next()) {
            String nickName = rs.getString("nick_name");
            System.out.println(id + ". " + nickName);
            System.out.println();
            id++;
        }
        stmt.close();
    }

    public void selectFullNamePhoneNumberUseName(String name) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT (first_name , last_name) AS full_name, phone_number FROM applicants WHERE first_name LIKE '%" +
                name + "%'");
        Integer id = 1;
        while (rs.next()) {
            String fullName = rs.getString("full_name");
            String phoneNumber = rs.getString("phone_number");
            System.out.println(id + ". " + fullName + " - " + phoneNumber);
            System.out.println();
            id++;
        }
        stmt.close();
    }

    public void selectFullNamePhoneNumberUseEmail(String email) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT (first_name , last_name) AS full_name, phone_number FROM applicants WHERE email LIKE '%" +
                email + "%'");
        Integer id = 1;
        while (rs.next()) {
            String fullName = rs.getString("full_name");
            String phoneNumber = rs.getString("phone_number");
            System.out.println(id + ". " + fullName + " - " + phoneNumber);
            System.out.println();
            id++;
        }
        stmt.close();
    }

    public void selectApplicantUseCode(Integer code) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM applicants WHERE application_code = " + code);
        Integer id = 1;
        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            Integer appCode = rs.getInt("application_code");
            System.out.println(id + ". " + firstName + " " + lastName + " " + phoneNumber + " " + email + " " + appCode);
            System.out.println();
            id++;
        }
        stmt.close();
    }

    public void updatePhoneNumberUseName(String firsName, String lastName, String phoneNumber) throws SQLException {
        stmt = c.createStatement();
        String sql = "UPDATE applicants SET phone_number = '" + phoneNumber + "' WHERE first_name LIKE '%" + firsName +
                "%' AND last_name LIKE '%" + lastName + "%';";
        stmt.executeUpdate(sql);
        System.out.println("Record Updated");
        System.out.println();
        stmt.close();
    }

    public void selectPhoneNumberUseName(String firstName, String lastName) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT phone_number FROM applicants WHERE first_name LIKE '%" + firstName +
                "%' AND last_name LIKE '%" + lastName + "%'");
        Integer id = 1;
        while (rs.next()) {
            String phoneNumber = rs.getString("phone_number");
            System.out.println(id + ". " + phoneNumber);
            System.out.println();
            id++;
        }
        stmt.close();
    }



    public void closeConnection(){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void advanceSearchApplicants(String searchPhrase) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from applicants WHERE first_name LIKE '%" + searchPhrase +
                "%' OR last_name LIKE '%" + searchPhrase + "%' OR phone_number LIKE '%" + searchPhrase +
                "%' OR email LIKE '%" + searchPhrase + "%' OR CAST(application_code AS TEXT) LIKE '%" + searchPhrase + "%';");
        Integer id = 1;
        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            Integer appCode = rs.getInt("application_code");
            System.out.println(id + ". " + firstName + " " + lastName + " " + phoneNumber + " " + email + " " + appCode);
            System.out.println();
            id++;
        }
        stmt.close();
    }

    public void advanceSearchMentors(String searchPhrase) throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from mentors WHERE first_name LIKE '%" + searchPhrase +
                "%' OR nick_name LIKE '%" + searchPhrase + "%' OR phone_number LIKE '%" + searchPhrase +
                "%' OR email LIKE '%" + searchPhrase + "%' OR city LIKE '%" + searchPhrase +
                "%' OR favourite_number LIKE '%" + searchPhrase + "%';");
        Integer id = 1;
        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String nickName = rs.getString("nick_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String city = rs.getString("city");
            Integer favouriteNumber = rs.getInt("favourite_number");
            System.out.println(id + ". " + firstName + " " + lastName + " " + nickName + " " + phoneNumber +
                    " " + email + " " + city + " " + favouriteNumber);
            System.out.println();
            id++;
        }
        stmt.close();
    }
}
