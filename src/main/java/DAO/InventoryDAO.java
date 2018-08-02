package DAO;

import BuisnessLogic.Artifact;
import BuisnessLogic.Quest;
import Connection.DatabaseConnection;
import Model.Codecooler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public void addArtefactToInventory(Artifact artifact, Codecooler codecooler) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO codecooler_artefact (id_codecooler, id_artefact)" +
                " values (?, ?)");

        stmt.setString(1, codecooler.getName());
        stmt.setString(2, artifact.);

        stmt.executeUpdate();

    }


    public List<Quest> getArtifactsOfCodecooler() throws SQLException {
        List<Quest> questList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM quest;");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            String questName = rs.getString("quest_name");
            String description = rs.getString("description");
            int coins = rs.getInt("coins");
            boolean isExtra = rs.getBoolean("isextra");

            Quest quest = new Quest(questName, description, coins, isExtra);

            questList.add(quest);
        }
        return questList;
    }

    public List<List> getInventory(List<Artifact> inventoryList) {
        List<List> listOfLists = new ArrayList<>();

        int size = 3;

        for (int i = 0; i < inventoryList.size(); i += size) {
            int end = Math.min(i + size, inventoryList.size());
            List<Artifact> sublist = inventoryList.subList(i, end);
            listOfLists.add(sublist);
        }

        return listOfLists;

    }

}