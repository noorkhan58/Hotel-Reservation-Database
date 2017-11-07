package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sampleUser.User;

public class UserManager {
    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "Evergreen2013";
    private static final String M_CONN_STRING =
            "jdbc:mysql://localhost/hotel";
    
	public static void displayAllRows() throws SQLException{
		String sql = "Select uID, uName, uStars from user";
		try (
			Connection conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		) {
			while(rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("uID") + ": ");
				bf.append(rs.getString("uName")+ " ");
				bf.append(rs.getInt("uStars"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static boolean insertUser(User user) throws SQLException {
		String sql = "insert into user (uName, uStars, membersince, banned) values"
				+ "(?, ?, ?, ?)";
		ResultSet rs = null;
		try(
				Connection conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setString(1, user.getuName());
			stmt.setInt(2, user.getuStars());
			stmt.setTimestamp(3, user.getMemberSince());
			stmt.setBoolean(4, user.isBanned());
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
				Connection conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
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

}
