package DAO;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import Connection.DatabaseConnection;
import Model.Codecooler;
import Model.Mentor;

import java.sql.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO {

    private Statement stmt;


    public Mentor getMentor(String loginFromForm) throws SQLException{
        Connection connection = DatabaseConnection.getInstance().getConnection();

        stmt = connection.createStatement();


        PreparedStatement stmt = connection.prepareStatement("SELECT * " +
                "FROM user_table WHERE login='" + loginFromForm + "';");

        ResultSet rs = stmt.executeQuery();

        Mentor mentor = null;

        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "mentor";
            mentor = new Mentor(firstName, lastName, login, password, email, role);

        }
        return mentor;
    }

    public List<Mentor> getMentorList() throws SQLException {
        List<Mentor> mentorList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user_table WHERE role='mentor';");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){

            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "mentor";

            Mentor mentor = new Mentor(firstName, lastName, login, password, email, role);

            mentorList.add(mentor);
        }

        return mentorList;
    }

}
