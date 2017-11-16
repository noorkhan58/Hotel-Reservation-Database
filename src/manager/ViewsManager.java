package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;

public class ViewsManager {
	
	public static void displayOpenRooms() throws SQLException {
		String sql = "Select * from OpenRooms";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("rNumber") + " ");
				bf.append(rs.getString("rType"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static void displayRoomTypes() throws SQLException {
		String sql = "Select * from RoomTypes";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("rNumber") + " ");
				bf.append(rs.getInt("amount"));
				System.out.println(bf.toString());
			}
		}
	}


}
