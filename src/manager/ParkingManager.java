package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleParking.Parking;

public class ParkingManager {
	/**
	 * displaces the parking in table format
	 * 
	 * @throws SQLException
	 */
	public static void dispalyParking() throws SQLException {
		String sql = "Select * from parking";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("pID") + " ");
				bf.append(rs.getString("uNAME") + ": ");
				bf.append(rs.getString("pStatus") + " ");
				bf.append(rs.getString("pType") + " ");
				bf.append(rs.getDate("startDate") + " ");
				bf.append(rs.getDate("endDate") + " ");
				System.out.println(bf.toString());
			}
		}
	}
	/**
	 * adds the parking into the table
	 * @param Parking the parking to add
	 * @return boolean if added or not
	 * @throws SQLException
	 */
	public static boolean insertUser(Parking Parking) throws SQLException {
		String sql = "insert into Parking(pNumber, uName, pStatus, pType, startDate, endDate) values"
				+ "(?,?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setInt(1, Parking.getpID());
			stmt.setString(2, Parking.getuName());
			stmt.setString(3, Parking.getpStatus());
			stmt.setString(4, Parking.getpType());
			stmt.setDate(5, Parking.getStartDate());
			stmt.setDate(6, Parking.getEndDate());
			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("No rows affected");
				return false;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	/**
	 * updates Facilities 
	 *  
	 * @param Facilities the Facilities to check
	 * @return boolean if done or not
	 * @throws SQLException
	 */
	public static boolean update(Parking Parking) throws SQLException {
		String sql = "Update Parking set uNAME = ?, pStatus = ?, pType = ?, StartDate = ?, EndDate = ?,  where pID = ?";

		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, Parking.getuName() );
			stmt.setString(2, Parking.getpStatus() );
			stmt.setString(3, Parking.getpType() );
			stmt.setDate(4, Parking.getStartDate() );
			stmt.setDate(5, Parking.getEndDate() );
			stmt.setInt(6, Parking.getpID());
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
	 * deletes parking spot in table
	 * 
	 * @param pID
	 *            the parking spot to delete
	 * @return returns if done or not
	 * @throws Exception
	 */
	public static boolean deleteParking(int pID) throws Exception {
		String sql = "Delete from parking where pID = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, pID);
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
