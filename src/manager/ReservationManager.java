package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleReservation.Reservation;

public class ReservationManager {
	/**
	 * displaces the reservation page
	 * @throws SQLException
	 */
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
	/**
	 * deletes the reservation
	 * @param reservationID the id to delete
	 * @return boolean of done
	 * @throws Exception
	 */
	public static boolean deleteReservation(int reservationID) throws Exception {
		String sql = "Delete from reservation where reservationID = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, reservationID);
			int affected = stmt.executeUpdate();
			if(affected == 1) {
				return true;
			}else {
				return false;
			}
		}
		catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		
	}
	
}
