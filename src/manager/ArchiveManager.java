package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SQLConnection;
import input.InputHelper;

public class ArchiveManager {
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
	
	public static void displayArchiveReservation() throws SQLException{
		String sql = "SELECT * FROM archiveReservation";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);){
			String sqlEnd = " WHERE ";
			String param = "";
			int option = InputHelper.getIntegerInput("Press the number for what you will you search: \n"
					+ "1 - All\n"
					+ "2 - Reservation ID\n"
					+ "3 - Username\n"
					+ "4 - Room Number\n"
					+ "5 - Checked In\n"
					+ "6 - Checked Out\n"
					+ "7 - Paid\n"
					+ "8 - Start Date\n"
					+ "9 - End Date\n");
			switch(option) {
			case 1:
				sqlEnd = "";
				param = "";
				break;
			case 2:
				param = "reservationID = " + InputHelper.getInput("Please enter in the ReservationID\n") + ";";
				break;
			case 3:
				param = "uName = '" + InputHelper.getInput("Please enter the Username\n") + "';";
				break;
			case 4:
				param = "rNumber = " + InputHelper.getInput("Please enter the Room Number\n") + ";";
				break;
			case 5:
				param = "CheckedIn = " + InputHelper.getInput("Please enter true/false\n") + ";";
				break;
			case 6:
				param = "CheckedOut = " + InputHelper.getInput("Please enter true/false\n") + ";";
				break;
			case 7:
				param = "paid = " + InputHelper.getInput("Please enter true/false\n") + ";";
				break;
			case 8:
				param = "startDate = " + InputHelper.getInput("Please enter the Start Date\n") + ";";
				break;
			case 9:
				param = "endDate = " + InputHelper.getInput("Please enter the Start Date\n") + ";";
				break;
			}
			sql = sql + sqlEnd + param;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getTimestamp("updatedAt") + "\t");
				bf.append(rs.getInt("reservationID") + "\t");
				bf.append(rs.getString("uName") + "\t");
				bf.append(rs.getInt("rNumber") + "\t");
				bf.append(rs.getBoolean("checkedIn") + "\t");
				bf.append(rs.getBoolean("checkedOut") + "\t");
				bf.append(rs.getBoolean("paid") + "\t");
				bf.append(rs.getDate("startDate") + "\t");
				bf.append(rs.getDate("endDate"));
				System.out.println(bf.toString());
			}
		}
		catch(SQLException e) {
			System.err.println(e);
		}
	}
}
