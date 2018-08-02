package DAO;

import BuisnessLogic.Room;
import Connection.DatabaseConnection;
import Model.CreepyGuy;
import Model.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreepyDAO {


    public List<CreepyGuy> getListOfAdmins() throws SQLException {
        List<CreepyGuy> listOfAdmins = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM user_table WHERE role='admin';");

        while(rs.next()) {

            Integer user_id = rs.getInt("id_user");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "admin";

            CreepyGuy admin = new CreepyGuy(firstName, lastName, login, password, email, role, user_id);

            listOfAdmins.add(admin);

        }


        return listOfAdmins;
    }

}
