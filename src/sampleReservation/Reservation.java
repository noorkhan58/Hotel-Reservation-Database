package sampleReservation;

import java.sql.Date;

public class Reservation {

	private int uID;
	private int rNumber;
	private int reservationID;
	private Date checkedIn;
	private Date checkedOut;
	private boolean paid;
	private Date startDate;
	private Date endDate;
	
	/**
	 * gets the users id on the reservation
	 * @return uID the users ID
	 */
	public int getuID() {
		return uID;
	}
	/**
	 * sets the user ID to room
	 * @param uID new user id
	 */
	public void setuID(int uID) {
		this.uID = uID;
	}
	/**
	 * gets the room number on reservation
	 * @return rNumber room number on reservation
	 */
	public int getrNumber() {
		return rNumber;
	}
	/**
	 * sets the room number on the reservation
	 * @param rNumber the room number for reservation
	 */
	public void setrNumber(int rNumber) {
		this.rNumber = rNumber;
	}
	/**
	 * gets the reservation indicator id
	 * @return reservationID the id for reservation
	 */
	public int getReservationID() {
		return reservationID;
	}
	/**
	 * sets the reservation id for the reservation
	 * @param reservationID sets the reservation ID 
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	/**
	 * gets status on user check in
	 * @return checkedIn if user has checked in
	 */
	public Date getCheckIn() {
		return checkedIn;
	}
	/**
	 * sets status for user check in
	 * @param checkIn if user has checked in or not
	 */
	public void setCheckIn(Date checkIn) {
		this.checkedIn = checkIn;
	}
	/**
	 * gets status on user check out
	 * @return checkedOut if user has checked out
	 */
	public Date getCheckOut() {
		return checkedOut;
	}
	/**
	 * sets status for user check out
	 * @param checkedOut if user has checked out or not
	 */
	public void setCheckOut(Date checkOut) {
		this.checkedOut = checkOut;
	}
	/**
	 * gets if user still has to pay
	 * @return paid if user has paid
	 */
	public boolean isPaid() {
		return paid;
	}
	/**
	 * sets status on user pay
	 * @param paid if user paid
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	/**
	 * gets the start date of reservation
	 * @return startDate  data to checkIn
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * sets the start date of reservation
	 * @param startDate  data to checkIn
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * gets the end date of reservation
	 * @return endDate data to checkout
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * sets the end date of the reservation
	 * @param endDate data to checkout
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
