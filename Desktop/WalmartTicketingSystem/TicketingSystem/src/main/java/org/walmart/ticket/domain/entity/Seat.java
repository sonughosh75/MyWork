package org.walmart.ticket.domain.entity;

import java.io.Serializable;

public class Seat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String seatNumber;

	private Integer rowNumber;

	private SeatLevel level;

	private Boolean isReserved;

	private Boolean isSeatOnHold;

	/**
	 * @return the isReserved
	 */
	public Boolean getIsReserved() {
		return isReserved;
	}

	/**
	 * @param isReserved
	 *            the isReserved to set
	 */
	public void setIsReserved(Boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * @return the isSeatOnHold
	 */
	public Boolean getIsSeatOnHold() {
		return isSeatOnHold;
	}

	/**
	 * @param isSeatOnHold
	 *            the isSeatOnHold to set
	 */
	public void setIsSeatOnHold(Boolean isSeatOnHold) {
		this.isSeatOnHold = isSeatOnHold;
	}

	/**
	 * @return the seatNumber
	 */
	public String getSeatNumber() {
		return seatNumber;
	}

	/**
	 * @param seatNumber
	 *            the seatNumber to set
	 */
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber
	 *            the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the level
	 */
	public SeatLevel getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(SeatLevel level) {
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		if (!(obj instanceof Seat))
			return false;
		Seat seat = (Seat) obj;
		if (!this.isReserved && !seat.getIsReserved() && (!this.isSeatOnHold && !seat.getIsSeatOnHold())) {
			return true;
		} else {
			return false;
		}
	}
}
