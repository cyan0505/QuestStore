package DAO;

import BuisnessLogic.Artifact;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtefactDAO {


    public void addArtifact(Artifact artifact) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO artifact (artefact_name, price, description, isgroup)");

        stmt.setString(1, artifact);
        stmt.setInt(2, artifact);
        stmt.setString(3, artifact);
        stmt.setBoolean(4, artifact);

        stmt.executeUpdate();

    }



    public Artifact getArtifact(int id) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artifact WHERE id='" + id + "');");

        Artifact artifact = new Artifact();

        return artifact;
    }


}
