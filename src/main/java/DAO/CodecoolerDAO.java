package DAO;

import BuisnessLogic.Artifact;
import Model.Codecooler;
import Connection.DatabaseConnection;
import Model.Mentor;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAO {

    private Statement stmt;

    public Codecooler getCodecooler(String loginFromForm) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        stmt = connection.createStatement();

        PreparedStatement stmt = connection.prepareStatement("SELECT * " +
                "FROM user_table WHERE login='" + loginFromForm + "';");

        ResultSet rs = stmt.executeQuery();
        Codecooler codecooler = null;

        while (rs.next()) {
            Integer id_user = rs.getInt("id_user");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "codecooler";
            codecooler = new Codecooler(firstName, lastName, login, password, email, role, id_user);
        }

        stmt = connection.prepareStatement("SELECT * " +
                "FROM codecooler WHERE id_user ='" + codecooler.getUserId() + "';");

        List<Integer> list = InventoryDAO.getArtifactsOfCodecooler(codecooler.getUserId());
        ArrayList<Artifact> artifactList = InventoryDAO.getListOfArtifact(list);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Integer coins = rs.getInt("coins");
            Integer exp = rs.getInt("experience");
            codecooler.setExp(exp);
            codecooler.setInventory(artifactList, coins);
        }
        return codecooler;
    }

    public List<Codecooler> getListOfCodecoolers() throws SQLException {
        List<Codecooler> codecoolerList = new ArrayList<>();

        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * " +
                "FROM user_table WHERE role='codecooler'");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Integer user_id = rs.getInt("id_user");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String role = "codecooler";


            Codecooler codecooler = new Codecooler(firstName, lastName, login, password, email, role, user_id);

            stmt = connection.prepareStatement("SELECT * " +
                    "FROM codecooler WHERE id_user ='" + codecooler.getUserId() + "';");

            List<Integer> list = InventoryDAO.getArtifactsOfCodecooler(codecooler.getUserId());
            ArrayList<Artifact> artifactList = InventoryDAO.getListOfArtifact(list);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Integer coins = rs.getInt("coins");
                Integer exp = rs.getInt("experience");
                codecooler.setExp(exp);
                codecooler.setInventory(artifactList, coins);
            }

            codecoolerList.add(codecooler);
        }

        return codecoolerList;
    }

    public Integer getCodecoolerId(Integer id) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * " +
                "FROM codecooler WHERE id_user ='" + id + "'");

        ResultSet rs = stmt.executeQuery();

        Integer user_id = null;

        while (rs.next()) {

            user_id = rs.getInt("id_codecooler");
        }
        return user_id;
    }
}
