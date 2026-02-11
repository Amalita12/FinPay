import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private  static final String URL="jdbc:mysql://localhost:3306/CINEMA";
    private  static final String username="root";
    private  static final String password="hayat@2004";
    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(URL, username, password);
    }
}