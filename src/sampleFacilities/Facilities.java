package sampleFacilities;

public class Facilities {

	private String fName;
	private int rNumber;
	private String fType;
	private String fStatus;
	private boolean handicap;
	/**
	 * gets the name of Facilities
	 * @return fName returns Facilities name
	 */
	public String getfName() {

		return fName;
	}
	/**
	 * sets the Facilities name
	 * @param fName input for Facilities name
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}
	/**
	 * gets the Facilities number
	 * @return rNumber returns the Facilities number
	 */
	public int getrNumber() {
		return rNumber;
	}
	/**
	 * sets the Facilities number
	 * @param rNumber input for new Facilities
	 */
	public void setrNumber(int rNumber) {
		this.rNumber = rNumber;
	}
	/**
	 * gets the Facilities type
	 * @return fType the Facilities type
	 */
	public String getfType() {
		return fType;
	}
	/**
	 * sets the Facilities type
	 * @param fType input of the new Facilities
	 */
	public void setfType(String fType) {
		this.fType = fType;
	}
	/**
	 * gets the Facilities status
	 * @return fStatus the Facilities status
	 */
	public String getfStatus() {
		return fStatus;
	}
	/**
	 * sets the Facilities status
	 * @param fStatus input Facilities status
	 */
	public void setfStatus(String fStatus) {
		this.fStatus = fStatus;
	}
	/**
	 * gets the handicap status
	 * @return Handicap current Facilities handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}
	/**
	 * changes handicap status 
	 * @param handicap new handicap status
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}
	
	
}
