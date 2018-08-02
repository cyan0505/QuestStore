package DAO;

import Model.Codecooler;
import Connection.DatabaseConnection;
import Model.Mentor;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAO {

    private Statement stmt;

    public Codecooler getCodecooler(String loginFromForm) throws SQLException{
        Connection connection = DatabaseConnection.getInstance().getConnection();

        stmt = connection.createStatement();

        PreparedStatement stmt = connection.prepareStatement("SELECT * " +
                "FROM user_table WHERE login='" + loginFromForm + "';");

        ResultSet rs = stmt.executeQuery();
        Integer id_user = null;
        Codecooler codecooler = null;

        while (rs.next()) {
            id_user = rs.getInt("id_user");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "codecooler";
            codecooler = new Codecooler(firstName, lastName, login, password, email, role);
        }

        stmt = connection.prepareStatement("SELECT * " +
                "FROM codecooler WHERE id_user ='" + id_user + "';");

        rs = stmt.executeQuery();

        while (rs.next()) {
            Integer coins = rs.getInt("coins");
            Integer exp = rs.getInt("experience");
            codecooler.setExp(exp);
            codecooler.setInventory(list, coins);
        }
        return codecooler;
    }

    public List<Codecooler> getListOfCodecoolers() throws SQLException{
        List<Codecooler> codecoolerList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT first_name, last_name, login, password, email" +
                "FROM user_table WHERE role='codecooler'");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {

            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "codecooler";


            Codecooler codecooler = new Codecooler(firstName, lastName, login, password, email, role);

            codecoolerList.add(codecooler);
        }

        return codecoolerList;
    }


}
