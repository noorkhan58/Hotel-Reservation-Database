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
	 * 
	 * @throws SQLException
	 */
	public static void dispalyReservation() throws SQLException {
		String sql = "Select * from reservation";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
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
	 * update reservation of existing 
	 * @param Reservation the reservation to updated
	 * @return boolean return if done or not
	 * @throws SQLException
	 */

	public static boolean update(Reservation Reservation) throws SQLException {
		String sql = "Update Reservation set uID = ?, rNumber = ?, CheckIn = ?, CheckOut = ?, Paid = ?, StartDate = ?, EndDate = ? where reservationID = ?";

		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, Reservation.getuID() );
			stmt.setInt(2, Reservation.getrNumber() );
			stmt.setBoolean(3, Reservation.getCheckIn() );
			stmt.setBoolean(4, Reservation.getCheckOut() );
			stmt.setBoolean(5, Reservation.isPaid() );
			stmt.setDate(6, Reservation.getStartDate() );
			stmt.setDate(7, Reservation.getEndDate() );
			stmt.setInt(8, Reservation.getReservationID());
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}

	/**
	 * deletes the reservation
	 * 
	 * @param reservationID
	 *            the id to delete
	 * @return boolean of done
	 * @throws Exception
	 */
	public static boolean deleteReservation(int reservationID) throws Exception {
		String sql = "Delete from reservation where reservationID = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, reservationID);
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}

}
