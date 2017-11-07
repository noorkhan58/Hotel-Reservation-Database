package sampleUser;

import java.sql.Timestamp;

public class User {
	private int uID;
	private String uName;
	private int uStars = 3;
	private Timestamp memberSince;
	private boolean isBanned = false;
	private int days;
	private int referrals;
	private int refrence;
	public int getuID() {
		return uID;
	}
	public void setuID(int uID) {
		this.uID = uID;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public int getuStars() {
		return uStars;
	}
	public void setuStars(int uStars) {
		this.uStars = uStars;
	}
	public Timestamp getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(Timestamp memberSince) {
		this.memberSince = memberSince;
	}
	public boolean isBanned() {
		return isBanned;
	}
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getReferrals() {
		return referrals;
	}
	public void setReferrals(int referrals) {
		this.referrals = referrals;
	}
	public int getRefrence() {
		return refrence;
	}
	public void setRefrence(int refrence) {
		this.refrence = refrence;
	}
	
	
	
	

}
