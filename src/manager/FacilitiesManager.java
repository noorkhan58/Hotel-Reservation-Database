package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleFacilities.Facilities;

public class FacilitiesManager {
	/**
	 * displaces the reservations in table
	 * 
	 * @throws SQLException
	 */
	public static void dispalyReservation() throws SQLException {
		String sql = "Select * from facilities";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("fName") + " ");
				bf.append(rs.getInt("rNumber") + ": ");
				bf.append(rs.getString("fType") + " ");
				bf.append(rs.getString("fStatus") + " ");
				bf.append(rs.getDate("startDate") + " ");
				bf.append(rs.getDate("endDate") + " ");
				System.out.println(bf.toString());
			}
		}
	}
	
	public static boolean insertUser(Facilities Facilities) throws SQLException {
		String sql = "insert into Facilities (fName, rNumber, fStatus, Handicap) values"
				+ "(?, ?, ?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, Facilities.getfName());
			stmt.setInt(2, Facilities.getrNumber());
			stmt.setString(3, Facilities.getfStatus());
			stmt.setBoolean(4, Facilities.isHandicap());
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
	 * updates a user
	 * 
	 * @param Facilities the facilities to updated
	 * @return returns if done or not
	 * @throws SQLException
	 */
	public static boolean update(Facilities Facilities) throws SQLException {
		String sql = "Update facilities set rNumber = ?, fType = ?, fStatus = ? where fName = ?";

		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, Facilities.getrNumber());
			stmt.setString(2, Facilities.getfType());
			stmt.setString(3, Facilities.getfStatus());
			stmt.setString(4, Facilities.getfName());
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
	 * deletes Facilities by name
	 * 
	 * @param fName
	 *            the Facilities to delete
	 * @return boolean if done or not
	 * @throws Exception
	 */
	public static boolean deleteFacilities(String fName) throws Exception {
		String sql = "Delete from facilities where fName = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, fName);
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

	public static void displayFacilities() {
		String sql = "Select * from facilities";
	}
}
