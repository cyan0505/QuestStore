package DAO;

import BuisnessLogic.Quest;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestDAO {

    public void addQuest(Quest quest) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO quest (quest_name, description, coins, isextra)" +
                                                                " values (?, ?, ?, ?)");

        stmt.setString(1, quest.getName());
        stmt.setString(2, quest.getDescription());
        stmt.setInt(3, quest.getReward());
        stmt.setBoolean(4, quest.getExtra());

        stmt.executeUpdate();

    }


    public Quest getQuest(int id) throws SQLException{
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM quest WHERE id_quest='"+ id + "');");

        ResultSet rs = stmt.executeQuery();

        String questName = rs.getString("quest_name");
        String description = rs.getString("description");
        int coins = rs.getInt("coins");
        boolean isExtra = rs.getBoolean("isextra");


        Quest quest = new Quest(questName, description, coins, isExtra);

        return quest;

    }

}
