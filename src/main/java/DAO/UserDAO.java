package DAO;

import Connection.DatabaseConnection;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {


    public void addUser(User user) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO user_table (first_name, last_name," +
                                                            "login, password, email, role) values (?, ?, ?, ?, ?, ?);");

        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
        stmt.setString(5, user.getEmail());
        stmt.setString(6, user.getRole());

        stmt.executeUpdate();

        connection.close();

    }
    
    public void deleteUser(int id) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("DELETE FROM user_table WHERE id_user='" + id + "');");

        stmt.executeUpdate();

        connection.close();

    }

}
