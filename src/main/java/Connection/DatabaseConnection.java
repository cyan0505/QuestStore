package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private Connection c;
    private Statement stmt;

    private static DatabaseConnection instance = new DatabaseConnection();

    private DatabaseConnection() {

    }

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "riczard", "1patryk"); // set user and password
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
