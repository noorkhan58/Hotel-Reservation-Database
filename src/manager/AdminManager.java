package manager;

import input.InputHelper;

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
	/**
	 * Shows archive up any updates or inserts on Reservation Table
	 *
	 */
	
	public static void displayArchiveUser() throws SQLException{
		String sql = "SELECT * FROM archiveUser";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);){
			String sqlEnd = " WHERE ";
			String param = "";
			int option = InputHelper.getIntegerInput("Press the number for what you will you search: \n"
					+ "1 - All\n"
					+ "2 - Username\n"
					+ "3 - Stars\n"
					+ "4 - Member Since\n"
					+ "5 - Banned\n"
					+ "6 - Days\n"
					+ "7 - Referrals\n"
					+ "8 - Reference\n");
			switch(option) {
			case 1:
				sqlEnd = "";
				param = "";
				break;
			case 2:
				param = "uName = '" + InputHelper.getInput("Please enter in the Username\n") + "';";
				break;
			case 3:
				param = "uStars = " + InputHelper.getInput("Please enter the number of Stars\n") + ";";
				break;
			case 4:
				param = "memberSince = " + InputHelper.getInput("Please enter the Member Since Date\n") + ";";
				break;
			case 5:
				param = "banned = " + InputHelper.getInput("Please enter true/false\n") + ";";
				break;
			case 6:
				param = "Days = " + InputHelper.getInput("Please enter the number of Days") + ";";
				break;
			case 7:
				param = "referrals = " + InputHelper.getInput("Please enter the number of Referrals") + ";";
				break;
			case 8:
				param = "reference = '" + InputHelper.getInput("Please enter the Username of the reference") + "';";
				break;
			}
			sql = sql + sqlEnd + param;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getTimestamp("updatedAt") + "\t");
				bf.append(rs.getString("uName") + "\t");
				bf.append(rs.getInt("uStars") + "\t");
				bf.append(rs.getDate("memberSince") + "\t");
				bf.append(rs.getBoolean("banned") + "\t");
				bf.append(rs.getInt("days") + "\t");
				bf.append(rs.getInt("referrals") + "\t");
				bf.append(rs.getString("reference") + "\t");
				System.out.println(bf.toString());
			}
		}
		catch(SQLException e) {
			System.err.println(e);
		}
	}
}
