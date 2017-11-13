package sampleRoom;

public class Room {

	private int rNumber;
	private String rStatus = "Available";
	private int price;
	private String rType;
	private boolean handicap = true;
	/**
	 * gets the room number
	 * @return rNumber the rooms number
	 */
	public int getrNumber() {
		return rNumber;
	}
	/**
	 * sets the room numer
	 * @param rNumber room unmber
	 */
	public void setrNumber(int rNumber) {
		this.rNumber = rNumber;
	}
	/**
	 * checks the status of room
	 * @param rStatus gets the status
	 */
	public String getrStatus() {
		return rStatus;
	}
	/**
	 * sets the status of room
	 * @param rStatus new room status
	 */
	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}
	/**
	 * gets the prices of room
	 * @return price room cost
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * sets the price of the room
	 * @param price room price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * gets the type of room
	 * @return rType room type
	 */
	public String getrType() {
		return rType;
	}
	/**
	 * sets the type of room
	 * @param rType the type of room
	 */
	public void setrType(String rType) {
		this.rType = rType;
	}
	/**
	 * gets if room is handicap or not
	 * @return handicap boolean if room is handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}
	/**
	 * sets the handicap status for room
	 * @param handicap status to set
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}
	
	
}
