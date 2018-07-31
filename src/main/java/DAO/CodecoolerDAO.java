package DAO;

import Model.Codecooler;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CodecoolerDAO {

    public void addCodecooler(Codecooler codecooler) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString(1, codecooler);
        stmt.setString();

        stmt.executeUpdate();

    }


    public Codecooler getCodecooler(int id) {
        return null;
    }
}
