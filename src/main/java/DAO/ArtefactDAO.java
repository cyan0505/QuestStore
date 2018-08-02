package DAO;

import BuisnessLogic.Artifact;
import Connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtefactDAO {

    private Statement stmt;

    public void addArtifact(Artifact artifact) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO artifact (artefact_name, price, description, isgroup)");

        stmt.setString(1, artifact.getName());
        stmt.setInt(2, artifact.getPrice());
        stmt.setString(3, artifact.getDescription());
        stmt.setBoolean(4, artifact.isGroup());

        stmt.executeUpdate();

    }


    public Artifact getArtifact(int id) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artifact WHERE id='" + id + "');");

        ResultSet rs = stmt.executeQuery();

        String artifactName = rs.getString("artefact_name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        boolean isGroup = rs.getBoolean("isgroup");


        Artifact artifact = new Artifact(artifactName, description, price, isGroup);

        return artifact;
    }


    public List<Artifact> getListOfArtifact() throws SQLException {

        List<Artifact> artifactList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM artefact;");

        while (rs.next()) {

            String artifactName = rs.getString("artefact_name");
            String description = rs.getString("description");
            int price = rs.getInt("price");
            boolean isGroup = rs.getBoolean("isgroup");

            Artifact artifact = new Artifact(artifactName, description, price, isGroup);

            artifactList.add(artifact);

        }
        System.out.println(artifactList.size());
        return artifactList;
    }


    public List<List> getNestedArtifactList(List<Artifact> artifactList) throws SQLException {
        List<List> listOfLists = new ArrayList<>();

        int size = 3;

        for (int i = 0; i < artifactList.size(); i += size) {
            int end = Math.min(i + size, artifactList.size());
            List<Artifact> sublist = artifactList.subList(i, end);
            listOfLists.add(sublist);
        }

        for (List list : listOfLists) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.indexOf(i));
            }
        }

        return listOfLists;
    }

}
