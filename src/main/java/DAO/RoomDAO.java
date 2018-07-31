package DAO;

import BuisnessLogic.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Connection.DatabaseConnection;

public class RoomDAO {

    public void createRoom(Room room) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();

        stmt.executeUpdate();

    }

}
