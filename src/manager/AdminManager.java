package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleAdmin.Admin;

public class AdminManager {

	/**
	 * log in to the system. using username and password
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static boolean login(String username, String password) throws SQLException {
	
		String sql = "Select * from admin where username ='" + username + "' and password = '" + password +"'";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
				if(!rs.next()) {
					return false;
				}
		}
		return true;
	}
	/**
	 * insert additional admin who can access the system
	 * @param admin
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertAdmin(Admin admin) throws SQLException {
		String sql = "CALL addAdmin(?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, admin.getUsername());
			stmt.setString(2, admin.getPassword());
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
	 * delete admin from database
	 * @param username
	 * @return
	 */
	public static boolean deleteAdmin(String username) {
		String sql = "Delete from admin where username = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, username);
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
