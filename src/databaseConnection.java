import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/finpay_db";
    private static final String USER = "root";
    private static final String PASSWORD = "amalbasbas123456";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection failed! " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

}
