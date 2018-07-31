package DAO;

import BuisnessLogic.Room;
import Connection.DatabaseConnection;
import Model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreepyDAO {

    public void addMentor(Mentor mentor) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();


        stmt.executeUpdate();
    }

    public void createRoom(Room room) throws SQLException{
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");

        stmt.setString();

        stmt.executeUpdate();

    }


    public Mentor getMentor(int id) {
        return null;
    }


}
