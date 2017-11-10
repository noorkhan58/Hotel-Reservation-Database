package sampleRoom;

public class Room {

	private int rNumber;
	private String rStatus = "Available";
	private int price;
	private String rType;
	private boolean handicap = true;
	
	public int getrNumber() {
		return rNumber;
	}
	public void setrNumber(int rNumber) {
		this.rNumber = rNumber;
	}
	public String getrStatus() {
		return rStatus;
	}
	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getrType() {
		return rType;
	}
	public void setrType(String rType) {
		this.rType = rType;
	}
	public boolean isHandicap() {
		return handicap;
	}
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}
	
	
}
