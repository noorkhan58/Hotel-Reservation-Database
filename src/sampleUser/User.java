package sampleUser;

import java.sql.Timestamp;

public class User {
	private String uName;
	private int uStars = 5;
	private Timestamp memberSince;
	private boolean isBanned = false;
	private int days = 0;
	private int referrals = 0;
	private String reference;

	/**
	 * gets the user name
	 * @return uName user name
	 */
	public String getuName() {
		return uName;
	}
	/**
	 * sets the user name
	 * @param uName user name
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}
	/**
	 * gets the user stars
	 * @return uStars u stars
	 */
	public int getuStars() {
		return uStars;
	}
	/**
	 * sets the user stars
	 * @param uStars user stars
	 */
	public void setuStars(int uStars) {
		this.uStars = uStars;
	}
	/**
	 * gets time stamp of user since
	 * @return memberSince
	 */
	public Timestamp getMemberSince() {
		return memberSince;
	}
	/**
	 * sets the time stamp for user
	 * @param memberSince  member date
	 */
	public void setMemberSince(Timestamp memberSince) {
		this.memberSince = memberSince;
	}
	/**
	 * checks if user is banned
	 * @return isBanned boolean ban check
	 */
	public boolean isBanned() {
		return isBanned;
	}
	/**
	 * changes user status of banned
	 * @param isBanned new banned status
	 */
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	/**
	 * gets the number of days user as spent
	 * @return days the amount of days
	 */
	public int getDays() {
		return days;
	}
	/**
	 * sets the amount of days a user has
	 * @param days the amount of days
	 */
	public void setDays(int days) {
		this.days = days;
	}
	/**
	 * gets the number of referrals user as spent
	 * @return referrals the amount of referrals
	 */
	public int getReferrals() {
		return referrals;
	}
	/**
	 * sets the amount of referrals a user has
	 * @param referrals the amount of referrals
	 */
	public void setReferrals(int referrals) {
		this.referrals = referrals;
	}
	/**
	 * gets the user refrence
	 * @return refrence the user that refrenced
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * sets a refrence for user
	 * @param refrence the user that refrenced
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	
	

}
