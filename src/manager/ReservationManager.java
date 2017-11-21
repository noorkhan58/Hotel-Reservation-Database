package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import connection.SQLConnection;
import input.InputHelper;
import sampleReservation.Reservation;

public class ReservationManager {
	/**
	 * displaces the reservation page
	 * 
	 * @throws SQLException
	 */
	public static void dispalyReservation() throws SQLException {
		String sql = "Select * from reservation";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("reservationID") + " ");
				bf.append(rs.getString("uName") + ": ");
				bf.append(rs.getInt("rNumber") + " ");
				bf.append(rs.getBoolean("checkedIn") + " ");
				bf.append(rs.getBoolean("checkedOut") + " ");
				bf.append(rs.getBoolean("paid") + " ");
				bf.append(rs.getDate("startDate") + " ");
				bf.append(rs.getDate("endDate"));
				System.out.println(bf.toString());
			}
		}
	}
	
	public static int getReservationId(String username) throws SQLException {
		String sql = "select reservationID from reservation where uName = '"+ username+"'";
		StringBuffer bf = new StringBuffer();
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				bf.append(rs.getInt("reservationID"));
			}
		}
		int reservationId = Integer.parseInt(bf.toString());
		return reservationId;
		
	}
	
	public static int getTotalRow(int roomNumber) throws SQLException {
		String sql = "Select count(*) as total  from reservation where rNumber = "+ roomNumber;
		StringBuffer bf = new StringBuffer();
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				bf.append(rs.getInt("total"));
			}
		}
		return Integer.parseInt(bf.toString());
	}
	
	public static int getValidDates(String date1, String date2, int roomNumber) throws SQLException {
		String sql = "Call getAvailabledates('"+date1+"', '"+ date2+"', "+ roomNumber+")";
		StringBuffer bf = new StringBuffer();
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			if (rs.next()) {
				bf.append(rs.getInt("total"));
			}
		}
		return Integer.parseInt(bf.toString());
	}
	
	public static boolean isValidDate(String date1, String date2, int roomNumber) throws SQLException {
		if(getValidDates(date1, date2, roomNumber) ==  getTotalRow(roomNumber)) {
			return true;
		}else{
			return false;
		}
	}
	/**
	 * adds a reveration into the table
	 * @param reservation the reservation to add
	 * @return boolean if done or not
	 * @throws SQLException
	 */
	public static boolean insertUser(Reservation reservation) throws SQLException {
		String sql = "insert into Reservation (uName,rNumber, CheckedIn, CheckedOut, paid, startDate, endDate) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, reservation.getuName());
			stmt.setInt(2, reservation.getrNumber());
			stmt.setBoolean(3, reservation.getCheckIn());
			stmt.setBoolean(4, reservation.getCheckOut());
			stmt.setBoolean(5, reservation.isPaid());
			if(reservation.getStartDate().equals(null) && reservation.getStartDate().equals(null)) {
				System.out.println("Invalid dates.");
			}else{
				stmt.setDate(6, reservation.getStartDate());
				stmt.setDate(7, reservation.getEndDate());
			}
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
	 * update reservation of existing 
	 * @param Reservation the reservation to updated
	 * @return boolean return if done or not
	 * @throws SQLException
	 */

	public static boolean update(Reservation Reservation, int reservationID) throws SQLException {
		String sql = "Update reservation set CheckedIn = ?, CheckedOut = ?, paid = ? where reservationID = " + reservationID;

		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setBoolean(1, Reservation.getCheckIn() );
			stmt.setBoolean(2, Reservation.getCheckOut() );
			stmt.setBoolean(3, Reservation.isPaid() );
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
	 * deletes the reservation
	 * 
	 * @param reservationID
	 *            the id to delete
	 * @return boolean of done
	 * @throws Exception
	 */
	public static boolean deleteReservation(String userName) throws Exception {
		String sql = "Delete from reservation where uName = ?";
		try (Connection conn = SQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, userName);
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

	public static String displayUnavailableDate(int roomNumber) throws SQLException {
		String sql = "Call displayUnavailableDate("+roomNumber+")";
		StringBuffer bf = new StringBuffer();
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				bf.append(rs.getDate("startDate") + " to ");
				bf.append(rs.getDate("endDate") + " . ");
			}
		}
		return bf.toString();
	}
	/**
	 * make new reservation
	 * @param newReservation
	 * @throws SQLException
	 */
	public static void makeReservation(Reservation newReservation) throws SQLException {
		String user = InputHelper.getInput("Enter Your/customer name: ");
		if(user.isEmpty()) {
			System.out.println("You must enter customer name");
		}else{
			newReservation.setuName(user);
		}
		if(checkBanned(user)) {
			System.out.println("The customer is banned. Customer not allowed to make reservation.");
			return;
		}
		RoomManager.displayAvailableRoom();
		int roomNumber = InputHelper.getIntegerInput("Select room number from above: ");

		if(roomNumber == 0) {
			System.out.println("You must enter room number: ");
		}else{
			newReservation.setrNumber(roomNumber);
		}
		String unavailableDate = displayUnavailableDate(roomNumber);
		if(unavailableDate.isEmpty()) {
			System.out.println();
		}else{
			System.out.println("Room " + roomNumber+ " is not available from "+ displayUnavailableDate(roomNumber)+"."
					+ " Please select date outside of this range");
		}
		
		String startDateString = InputHelper.getInput("Please enter start date(YYYY-MM-DD): ");
		String endDateString = InputHelper.getInput("Please enter end date(YYYY-MM-DD): ");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStart = null;
		java.util.Date dateEnd = null;
		
		try {
			dateStart = df.parse(startDateString);
			dateEnd = df.parse(endDateString);
		} catch (ParseException e) {
			System.err.println("You must enter valid dates\n");
			makeReservation(newReservation);
		}
		boolean checkDates = isValidDate(startDateString, endDateString, newReservation.getrNumber());
		java.sql.Date sqlStartDate = new java.sql.Date(dateStart.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(dateEnd.getTime());
		
		if(checkDates) {
			newReservation.setStartDate(sqlStartDate);
			newReservation.setEndDate(sqlEndDate);
			newReservation.setCheckIn(false);
			System.out.println("Customer pay when check in");
			newReservation.setPaid(false);
			newReservation.setCheckOut(false);
			boolean result = ReservationManager.insertUser(newReservation);
			if(result) {
				System.out.println("Reservation Successful");
			}else{
				System.out.println("Whoops, Something wrong. Reservation not complete");
			}
		}else{
			System.out.println("Invalid Dates. Please try again later");
			makeReservation(newReservation);
			
		}	
		
	}

	
	public static boolean checkBanned(String name) throws SQLException {
		String sql = "Call checkBanUser('"+name+ "')";
		try (Connection conn = SQLConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			 if(rs.next()) {
				return true;
			}
	}
		return false;
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
