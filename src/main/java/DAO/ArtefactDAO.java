package DAO;

import BuisnessLogic.Artifact;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtefactDAO {


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

//    public int getNumberOfArtifact() throws SQLException{
//
//
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//
//        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM artifact;");
//
//        ResultSet rs = stmt.executeQuery();
//
//        int artifactNumber = stmt;
//
//        return artifactNumber;
//
//    }

    public List<Artifact> getListOfArtifact() throws SQLException{

        List<Artifact> artifactList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artifact;");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {

            String artifactName = rs.getString("artefact_name");
            String description = rs.getString("description");
            int price = rs.getInt("price");
            boolean isGroup = rs.getBoolean("isgroup");

            Artifact artifact = new Artifact(artifactName, description, price, isGroup);

            artifactList.add(artifact);

        }

        return artifactList;
    }
    
}
