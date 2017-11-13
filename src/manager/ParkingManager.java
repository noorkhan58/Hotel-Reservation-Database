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
	 * @throws SQLException
	 */
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
	/**
	 * deletes parking spot in table
	 * @param pID the parking spot to delete
	 * @return returns if done or not
	 * @throws Exception
	 */
	public static boolean deleteParking(int pID) throws Exception {
		String sql = "Delete from parking where pID = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, pID);
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
