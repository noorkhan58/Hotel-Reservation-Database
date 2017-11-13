package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleFacilities.Facilities;
import sampleUser.User;

public class FacilitiesManager {
	/**
	 * displaces the reservations in table
	 * @throws SQLException
	 */
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
					bf.append(rs.getInt("rNumber") + ": ");
					bf.append(rs.getString("fType")+ " ");
					bf.append(rs.getString("fStatus")+ " ");
					bf.append(rs.getDate("startDate") + " ");
					bf.append(rs.getDate("endDate") + " ");	
					System.out.println(bf.toString());
				}
			}
	}
	/**
	 * deletes Facilities by name
	 * @param fName the Facilities to delete
	 * @return boolean if done or not
	 * @throws Exception
	 */
	public static boolean deleteFacilities(String fName) throws Exception {
		String sql = "Delete from facilities where fName = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setString(1, fName);
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
