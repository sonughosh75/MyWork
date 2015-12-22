package org.walmart.ticket.domain.value;

public class SeatHoldRequest {
	
	private Integer numSeats;
	private Integer minLevel;
	private Integer maxLevel;
	private String customerEmail;
	
	/**
	 * @return the minLevel
	 */
	public Integer getMinLevel() {
		return minLevel;
	}
	/**
	 * @param minLevel the minLevel to set
	 */
	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}
	/**
	 * @return the maxLevel
	 */
	public Integer getMaxLevel() {
		return maxLevel;
	}
	/**
	 * @param maxLevel the maxLevel to set
	 */
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	/**
	 * @return the numSeats
	 */
	public Integer getNumSeats() {
		return numSeats;
	}
	/**
	 * @param numSeats the numSeats to set
	 */
	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}
	
	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}
	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
