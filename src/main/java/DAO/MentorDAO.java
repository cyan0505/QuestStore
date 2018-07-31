package DAO;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import Connection.DatabaseConnection;
import Model.Codecooler;
import java.sql.*;

import java.sql.Connection;

public class MentorDAO {

    public void addCodecooler(Codecooler codecooler) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString(1, codecooler);
        stmt.setString();

        stmt.executeUpdate();

    }


    public void addQuest(Quest quest) {


    }


    public void addArtifact(Artifact artifact) {

    }


    public Codecooler getCodecooler(int id) {
        return null;
    }

    public Quest getQuest(int id) {
        return null;
    }


    public Artifact getArtifact(int id) {
        return  null;
    }




}
