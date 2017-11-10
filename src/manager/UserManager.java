package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.SQLConnection;
import sampleUser.User;

public class UserManager {
    
	public static void displayAllRows() throws SQLException{
		String sql = "Select * from user";
		try (
			Connection conn = SQLConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		) {
			while(rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("uID") + ": ");
				bf.append(rs.getString("uName")+ " ");
				bf.append(rs.getInt("uStars"));
				bf.append("\t");
				bf.append(rs.getDate("memberSince"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static boolean insertUser(User user) throws SQLException {
		String sql = "insert into user (uName,uStars, membersince, banned, days, Referrals, refrence) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setString(1, user.getuName());
			stmt.setInt(2, 5);
			stmt.setTimestamp(3, user.getMemberSince());
			stmt.setBoolean(4, false);
			stmt.setInt(5, user.getDays());
			stmt.setInt(6, user.getReferrals());
			stmt.setInt(7, user.getRefrence());
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				rs = stmt.getGeneratedKeys();
				rs.next();
				int newKey = rs.getInt(1);
				user.setuID(newKey);
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
		return true;
	}
	
	public static boolean update(User user) throws SQLException {
		String sql = "Update user set uName = ?, uStars = ?, memberSince = ? where uID = ?";
		
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			
				stmt.setString(1, user.getuName());
				stmt.setInt(2, user.getuStars());
				stmt.setTimestamp(3, user.getMemberSince());
				stmt.setInt(4, user.getuID());
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
	
	public static boolean deleteUser(int userID) throws Exception {
		String sql = "Delete from user where uID = ?";
		try(
				Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, userID);
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
