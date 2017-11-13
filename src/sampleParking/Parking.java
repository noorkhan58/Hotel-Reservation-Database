package sampleParking;

import java.sql.Date;

public class Parking {

	private int pID;
	private int uID;
	private String pStatus;
	private String pType;
	private Date startDate;
	private Date endDate;
	/**
	 * gets the pID
	 * @return pID current Parking ID
	 */
	public int getpID() {
		return pID;
	}
	/**
	 * sets the Parking id
	 * @param pID new Parking id
	 */
	public void setpID(int pID) {
		this.pID = pID;
	}
	/**
	 * uID on the Parking spot
	 * @return uID user on spot
	 */
	public int getuID() {
		return uID;
	}
	/**
	 * sets the users ID on spot
	 * @param uID user to get spot for
	 */
	public void setuID(int uID) {
		this.uID = uID;
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
	public String getpType() {
		return pType;
	}
	/**
	 * sets Parking type
	 * @param pType Parking type
	 */
	public void setpType(String pType) {
		this.pType = pType;
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
