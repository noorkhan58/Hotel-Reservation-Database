package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;

public class ReservationManager {

	public static void dispalyReservation() throws SQLException {
		String sql = "Select * from reservation";
		try (
				Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			) {
				while(rs.next()) {
					StringBuffer bf = new StringBuffer();
					bf.append(rs.getInt("uID") + ": ");
					bf.append(rs.getInt("rNumber") + " ");
					bf.append(rs.getInt("reservationID") + " ");
					bf.append(rs.getDate("checkedIn") + " ");
					bf.append(rs.getDate("checkedOut") + " ");
					bf.append(rs.getBoolean("paid") + " ");
					bf.append(rs.getDate("startDate") + " ");
					bf.append(rs.getDate("endDate"));
					System.out.println(bf.toString());
				}
			}
	}
}
