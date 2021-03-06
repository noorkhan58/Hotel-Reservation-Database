package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import connection.SQLConnection;
import sampleReservation.Reservation;
import sampleRoom.Room;

public class RoomManager {
	/**
	 * displays the rooms tale
	 * 
	 * @throws SQLException
	 *             error
	 */
	public static void displayOpenRooms() throws SQLException {
		String sql = "select * from rooms where rStatus = 'Available'";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("rNumber") + ": ");
				bf.append(rs.getString("rStatus") + " ");
				bf.append("$");
				bf.append(rs.getInt("price"));
				bf.append(" ");
				bf.append(rs.getString("rType"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static void displayAllRoomNumber() throws SQLException {
		String sql = "select rNumber from rooms";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			int i = 0;
			StringBuffer bf = new StringBuffer();
			while (rs.next()) {
				bf.append(rs.getInt("rNumber") + ", ");
				i++;
				if(i%10 == 0) {
					bf.append("\n");
				}

			}
			System.out.println(bf.toString());
		}
	}
	
	public static void displayAllrType() throws SQLException {
		String sql = "select rType, price from rooms";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("rType") + ", $");
				bf.append(rs.getInt("price") + ", ");
				System.out.println(bf.toString());
			}
		}
	}
	
	
	public static void displayHandicapRooms() throws SQLException {
		String sql = "select rNumber, rType from rooms where rStatus = 'Available' and Handicap = 1";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append("Room: "+rs.getInt("rNumber")+" Type: "+ rs.getString("rType") + " Is handicap ");
				System.out.println(bf.toString());
			}
		}
	}
	
public static int getCostOfRoom(int reservationID) throws SQLException{
	String sql = "CALL GetPrices(" + reservationID + ")";
	int price =0; int daycount =0; 
	try (Connection conn = SQLConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);) {
		if (rs.next()) {
			Date start = rs.getDate("startDate");
			Date end = rs.getDate("endDate");
			String room = rs.getString("rNumber");
			price = rs.getInt("price");
			
			System.out.println("Reservation selected for room " + room +" for dates: "+start.toString()+" - "+end.toString());
		}
	}
	daycount = getStayDays(reservationID);
	return price * daycount;
}

public static int getStayDays(int reservationID)throws SQLException{
String sql = "Select * from ReservationDays where reservationID = "+ reservationID;
int daycount =0;
try (Connection conn = SQLConnection.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);) {
	if (rs.next()) {
		int rID = rs.getInt("reservationID");
		daycount = rs.getInt("daycount");
	}
}
return daycount;
}
	public static void displayOpenRoomsType() throws SQLException {
		String sql = "select * from RoomTypes";
		int i = 1;
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();	
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("rType"));
				bf.append((": "));
				bf.append(rs.getInt("Amount"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static void displayRoomTypes() throws SQLException {
		String sql = "select * from RoomTypes where not rtype = 'Facilities' group by rType";
		int i = 1;
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();	
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("rType"));
				bf.append((": "));
				bf.append(rs.getInt("Amount"));
				System.out.println(bf.toString());
			}
		}
	}

	/**
	 * adds a room into the table
	 * 
	 * @param room
	 *            the room to input into sql table
	 * @return boolean if table was inserted
	 * @throws SQLException
	 *             error
	 */
	public static boolean insertRoom(Room room) throws SQLException {
		String sql = "insert into Rooms (rNumber, rStatus, price, rType, handicap)"
				+ "values(?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setInt(1, room.getrNumber());
			stmt.setString(2, room.getrStatus());
			stmt.setInt(3, room.getPrice());
			stmt.setString(4, room.getrType());
			stmt.setBoolean(5, room.isHandicap());
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
	 * updates the room in the table
	 * 
	 * @param room
	 *            the room to update in the table
	 * @return returns if updated
	 */
	public static boolean updateRoom(Room room) {
		String sql = "Update rooms set rStatus = ?, price = ? where rNumber = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, room.getrStatus());
			stmt.setInt(2, room.getPrice());
			stmt.setInt(3, room.getrNumber());
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
	 * deletes a room from the table
	 * 
	 * @param rNumber
	 *            the room to delete
	 * @return returns if delete or not
	 */
	public static boolean deleteRoom(int rNumber) throws SQLException{
		String sql = "Delete from Parking where roomNumber = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, rNumber);
			int affected = stmt.executeUpdate();}
		//System.out.println("yeeh");
		sql = "Delete from rooms where rNumber = ?";
		try (Connection conn2 = SQLConnection.getConnection();
				PreparedStatement stmt2 = conn2.prepareStatement(sql);) {
			stmt2.setInt(1, rNumber);
			int affected = stmt2.executeUpdate();
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
	
	public static void displayAvailableRoom(String s) throws SQLException {
		StringBuffer bf = new StringBuffer();
		bf.append("select * from rooms where rType = '");
		bf.append(s + "'");
		String sql = bf.toString();
		//System.out.println(bf.toString());
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				bf = new StringBuffer();
				bf.append(rs.getInt("rNumber") + ": ");
				bf.append(rs.getString("rStatus") + " ");
				bf.append("$");
				bf.append(rs.getInt("price"));
				bf.append(" ");
				bf.append(rs.getString("rType"));
				System.out.println(bf.toString());
			}
		}
    }
	
	public static void showRoomTypeOnly() throws SQLException{
		String sql = "select rType from rooms where not rType = 'Facilities' group by rType";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			StringBuffer bf = new StringBuffer();
			while (rs.next()) {
				bf.append(rs.getString("rType") + ", ");
			}
			System.out.println(bf.toString());
		}
	}
	
	public static void showRoomTypes() throws SQLException{
		String sql = "select * from rooms where rStatus = 'Available'";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("rNumber") + ": ");
				bf.append(rs.getString("rStatus") + " ");
				bf.append("$");
				bf.append(rs.getInt("price"));
				bf.append(" ");
				bf.append(rs.getString("rType"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static void allAvailableroomsFacilities() throws SQLException {
		String sql = "Select * from allAvailableroomsFacilities";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("rNumber") + ": ");
				bf.append("\t\t");
				bf.append(rs.getString("rType"));
				System.out.println(bf.toString());
			}
		}
	}
}
