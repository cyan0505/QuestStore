package DAO;

import BuisnessLogic.Room;
import Connection.DatabaseConnection;
import Model.CreepyGuy;
import Model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreepyDAO {

    public void addCreepyGuy(CreepyGuy creepyGuy) throws SQLException{

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("");




    }

}
