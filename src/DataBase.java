import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private static final String URL = "jdbc:mysql://localhost:3307/cinema";
    private static final String USER = "root";
    private static final String PASSWORD = "uxui2025";

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
