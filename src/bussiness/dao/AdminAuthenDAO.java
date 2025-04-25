package bussiness.dao;

import bussiness.entity.Admin;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAuthenDAO {
    private static AdminAuthenDAO instance;
    private static final String GET_ACCOUNT = "SELECT username, password FROM admin WHERE username = ? AND password = ?";
    private static String username = null;
    private static String password = null;

    public static AdminAuthenDAO getInstance() {
        return instance;
    }

    public static Admin adminAuthen() {
        // lấy kết nối tới csdl
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(GET_ACCOUNT);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Admin(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUsername(String username) {
        AdminAuthenDAO.username = username;
    }

    public static void setPassword(String password) {
        AdminAuthenDAO.password = password;
    }
}
