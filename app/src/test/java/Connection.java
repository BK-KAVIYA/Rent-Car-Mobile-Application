import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {

    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12627023";
    private static final String USERNAME = "sql12627023";
    private static final String PASSWORD = "tDAdCYS6Uj";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
