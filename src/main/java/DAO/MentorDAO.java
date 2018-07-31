package DAO;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import Connection.DatabaseConnection;
import Model.Codecooler;
import Model.Mentor;

import java.sql.*;

import java.sql.Connection;

public class MentorDAO {


    public void addMentor(Mentor mentor) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();


        stmt.executeUpdate();
    }


    public Mentor getMentor(int id) {
        return null;
    }



}
