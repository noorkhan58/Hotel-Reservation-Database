package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

	private static final String USERNAME = "root";
    private static final String PASSWORD = "Evergreen2013";
    private static final String M_CONN_STRING =
            "jdbc:mysql://localhost/hotel";
    
    public static Connection getConnection() {
    	Connection conn = null;
		try {
			conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.err.println(e);
		}
    	return conn;
    }
}
