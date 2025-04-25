package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/phone_store_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi khi kết nối tới CSDL "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối CSDL "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("kết nối thành công tới csdl");
            closeConnection(con);
        } else {
            System.out.println("kết nối thất bại tới csdl");
        }
    }
}
