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
	public int getuID() {
		return uID;
	}
	public void setuID(int uID) {
		this.uID = uID;
	}
	public int getrNumber() {
		return rNumber;
	}
	public void setrNumber(int rNumber) {
		this.rNumber = rNumber;
	}
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public Date getCheckIn() {
		return checkedIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkedIn = checkIn;
	}
	public Date getCheckOut() {
		return checkedOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkedOut = checkOut;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
