package DAO;

import BuisnessLogic.Artifact;
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

        stmt.setInt(1, codecooler.getUserId());
        stmt.setInt(2, artifact.getArtifactId());

        stmt.executeUpdate();

    }


    public List<Integer> getArtifactsOfCodecooler(int codecoolerId) throws SQLException {
        List<Integer> artefactIdList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM codecooler_artefact WHERE id_codecooler='" +
                codecoolerId + ";");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int artifactId = rs.getInt("id_artefact");
            artefactIdList.add(artifactId);

        }

        return artefactIdList;
    }


    public List<Artifact> getListOfArtifact(List<Integer> artifactIdList) throws SQLException {
        List<Artifact> artifactList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        for(int i = 0; i < artifactIdList.size(); i++) {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artefact WHERE id='"
                    + artifactIdList.get(i) + "';");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String artifactName = rs.getString("artefact_name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                boolean isGroup = rs.getBoolean("isgroup");
                int artifactId = rs.getInt("id_artefact");

                Artifact artifact = new Artifact(artifactName, description, price, isGroup, artifactId);

                artifactList.add(artifact);
            }
        }

        return artifactList;
    }


    public List<List> getInventory(List<Artifact> artifactList) {
        List<List> listOfLists = new ArrayList<>();

        int size = 3;

        for (int i = 0; i < artifactList.size(); i += size) {
            int end = Math.min(i + size, artifactList.size());
            List<Artifact> sublist = artifactList.subList(i, end);
            listOfLists.add(sublist);
        }

        return listOfLists;

    }

}