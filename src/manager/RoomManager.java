package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleRoom.Room;

public class RoomManager {
	/**
	 * displays the rooms tale
	 * @throws SQLException error
	 */
	public static void displayRooms() throws SQLException {
		String sql = "Select * from rooms";
		try (
				Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			) {
				while(rs.next()) {
					StringBuffer bf = new StringBuffer();
					bf.append(rs.getInt("rNumber") + ": ");
					bf.append(rs.getString("rStatus")+ " ");
					bf.append("$");
					bf.append(rs.getInt("price"));
					bf.append(" ");
					bf.append(rs.getString("rType"));
					System.out.println(bf.toString());
				}
			}
	}
	/**
	 * adds a room into the table
	 * @param room the room to input into sql table
	 * @return boolean if table was inserted
	 * @throws SQLException error
	 */
	public static boolean insertRoom(Room room) throws SQLException {
		String sql = "insert into Rooms (rNumber, rStatus, price, rType, handicap)"
				+ "values(?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setInt(1, room.getrNumber());
			stmt.setString(2, room.getrStatus());
			stmt.setInt(3, room.getPrice());
			stmt.setString(4, room.getrType());
			stmt.setBoolean(5, true);
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				return true;
			}else {
				System.err.println("No rows affected");
				return false;
			}
			
		}catch (SQLException e) {
			System.err.println(e);
			return false;
		}finally {
		if(rs != null) {
			rs.close();
		}
		}
	}
	/**
	 * updates the room in the table
	 * @param room the room to update in the table
	 * @return returns if updated
	 */
	public static boolean updateRoom(Room room) {
		String sql = "Update rooms set rStatus = ?, price = ? where rNumber = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			
				stmt.setString(1, room.getrStatus());
				stmt.setInt(2, room.getPrice());
				stmt.setInt(3, room.getrNumber());
				int affected = stmt.executeUpdate();
				if(affected == 1) {
					return true;
				}else{
					return false;
				}
		}catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
	/**
	 * deletes a room from the table
	 * @param rNumber the room to delete
	 * @return returns if delete or not
	 */
	public static boolean deleteRoom(int rNumber) {
		String sql = "Delete from rooms where rNumber = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, rNumber);
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
