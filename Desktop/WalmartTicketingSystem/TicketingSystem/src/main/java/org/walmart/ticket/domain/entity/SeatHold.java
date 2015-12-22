package org.walmart.ticket.domain.entity;

import java.util.Set;

public class SeatHold {
	
	private String seatHoldId;
	private Integer levelId;
	private String customerEmail;
	private Set<Seat> seats;
	
	/**
	 * @return the seats
	 */
	public Set<Seat> getSeats() {
		return seats;
	}
	/**
	 * @param seats the seats to set
	 */
	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}
	/**
	 * @return the seatHoldId
	 */
	public String getSeatHoldId() {
		return seatHoldId;
	}
	/**
	 * @param seatHoldId the seatHoldId to set
	 */
	public void setSeatHoldId(String seatHoldId) {
		this.seatHoldId = seatHoldId;
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
	
	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
}
