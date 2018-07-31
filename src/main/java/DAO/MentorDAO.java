package DAO;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import Connection.DatabaseConnection;
import Model.Codecooler;
import Model.Mentor;

import java.sql.*;

import java.sql.Connection;

public class MentorDAO {

    private Statement stmt;

//
//    public void addMentor() throws SQLException {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//
//        PreparedStatement stmt = connection.prepareStatement("");
//
//        stmt.setString();
//
//
//        stmt.executeUpdate();
//    }


    public Mentor getMentor(int id) throws SQLException{
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
        String role = "mentor";


        Mentor mentor = new Mentor(firstName, lastName, login, password, email, role);

        return mentor;

    }



}
