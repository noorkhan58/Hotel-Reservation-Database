package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleParking.Parking;

public class ParkingManager {
	public static void dispalyParking() throws SQLException {
		String sql = "Select * from parking";
		try (
				Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			) {
				while(rs.next()) {
					StringBuffer bf = new StringBuffer();
					bf.append(rs.getInt("pID") + " ");
					bf.append(rs.getInt("uID") + ": ");
					bf.append(rs.getString("pStatus")+ " ");
					bf.append(rs.getString("pType")+ " ");
					bf.append(rs.getDate("startDate") + " ");
					bf.append(rs.getDate("endDate") + " ");
					System.out.println(bf.toString());
				}
			}
	}

}
