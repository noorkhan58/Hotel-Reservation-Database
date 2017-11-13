package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;

public class FacilitiesManager {
	public static void dispalyReservation() throws SQLException {
		String sql = "Select * from facilities";
		try (
				Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			) {
				while(rs.next()) {
					StringBuffer bf = new StringBuffer();
					bf.append(rs.getString("fName")+ " ");
					bf.append(rs.getInt("fNumber") + ": ");
					bf.append(rs.getString("fType")+ " ");
					bf.append(rs.getString("fStatus")+ " ");
					bf.append(rs.getDate("startDate") + " ");
					bf.append(rs.getDate("endDate") + " ");	
					System.out.println(bf.toString());
				}
			}
	}
	

}
