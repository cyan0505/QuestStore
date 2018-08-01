package DAO;

import BuisnessLogic.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.DatabaseConnection;

public class RoomDAO {

    public void createRoom(Room room) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO room (room_name) values (?);");

        stmt.setString(1, room.getName());

        stmt.executeUpdate();

    }


    public Room getRoom(int id) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT room_name FROM room WHERE id_room='" + id + "');");

        ResultSet rs = stmt.executeQuery();

        String roomName = rs.getString("room_name");


        return new Room(roomName);
    }


}
