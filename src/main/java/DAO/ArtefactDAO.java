package DAO;

import BuisnessLogic.Artifact;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtefactDAO {


    public void addArtifact(Artifact artifact) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();

        stmt.executeUpdate();

    }



    public Artifact getArtifact(int id) {
        return  null;
    }


}
