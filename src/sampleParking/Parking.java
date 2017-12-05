package sampleParking;

import java.sql.Date;

public class Parking {

	private int pNumber;
	private String uName;
	private String pStatus;
	private int roomNumber;
	private Date startDate;
	private Date endDate;
	/**
	 * gets the pID
	 * @return pID current Parking ID
	 */
	public int getpNumber() {
		return pNumber;
	}
	/**
	 * sets the Parking id
	 * @param pID new Parking id
	 */
	public void setpNumber(int pNumber) {
		this.pNumber = pNumber;
	}
	/**
	 * uID on the Parking spot
	 * @return uID user on spot
	 */
	public String getuName() {
		return uName;
	}
	/**
	 * sets the users ID on spot
	 * @param uID user to get spot for
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}
	/**
	 * gets the status of Parking
	 * @return pStatus Parking status
	 */
	public String getpStatus() {
		return pStatus;
	}
	/**
	 * sets Parking status
	 * @param pStatus Parking status
	 */
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	/**
	 * gets Parking type
	 * @return pType Parking type
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
	/**
	 * sets Parking type
	 * @param pType Parking type
	 */
	public void setRnumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	/**
	 * gets the state date of reservation of spot
	 * @return startDate data of reservation
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * sets the Parking reservation
	 * @param startDate data of reservation of Parking
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * gets the end date of reservation of spot
	 * @return endDAte data of reservation
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * sets the Parking reservation
	 * @param endDate data of reservation of Parking
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
