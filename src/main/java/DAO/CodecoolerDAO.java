package DAO;

import Model.Codecooler;
import Connection.DatabaseConnection;
import Model.Mentor;

import java.sql.*;

public class CodecoolerDAO {

    private Statement stmt;

//    public void addCodecooler(Codecooler codecooler) throws SQLException {
//
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//
//        PreparedStatement stmt = connection.prepareStatement("");
//
//        stmt.setString(1, codecooler);
//        stmt.setString();
//
//        stmt.executeUpdate();
//
//        connection.close();
//
//    }


    public Codecooler getCodecooler(int id) throws SQLException{
        Connection connection = DatabaseConnection.getInstance().getConnection();

        stmt = connection.createStatement();


        PreparedStatement stmt = connection.prepareStatement("SELECT first_name, last_name, login, password, email" +
                "FROM user_table WHERE role='codecooler'");

        ResultSet rs = stmt.executeQuery();


        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String login = rs.getString("login");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String role = "codecooler";


        Codecooler codecooler = new Codecooler(firstName, lastName, login, password, email, role);

        return codecooler;
    }
}
