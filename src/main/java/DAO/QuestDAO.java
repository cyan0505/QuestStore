package DAO;

import BuisnessLogic.Quest;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestDAO {

    public void addQuest(Quest quest) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();

        stmt.executeUpdate();

    }


    public Quest getQuest(int id) {
        return null;
    }

}
