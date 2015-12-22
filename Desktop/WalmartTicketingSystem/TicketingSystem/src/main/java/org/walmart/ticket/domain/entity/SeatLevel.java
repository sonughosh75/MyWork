package org.walmart.ticket.domain.entity;

import java.util.Set;

public class SeatLevel {
	
	private Integer levelId;

	private String levelName;
	
	private Double price;
	
	private Integer totalRows;
	
	private Integer seatsInEachRow;
	
	private Set<Seat> seats;
	
	/**
	 * @return the totalRows
	 */
	public Integer getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

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

	public SeatLevel(){}
	
	public SeatLevel(Integer levelId) {
		this.levelId = levelId;
	}
	
	public SeatLevel(int levelId, String name, Double price,
			int numOfRows, int seatsPerRow){
		this.levelId = levelId;
		this.levelName = name;
		this.price = price;
		this.totalRows = numOfRows;
		this.seatsInEachRow = seatsPerRow;
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
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the numberOfRows
	 */
	public Integer getNumberOfRows() {
		return totalRows;
	}
	/**
	 * @param numberOfRows the numberOfRows to set
	 */
	public void setNumberOfRows(Integer numberOfRows) {
		this.totalRows = numberOfRows;
	}
	/**
	 * @return the seatsInEachRow
	 */
	public Integer getSeatsInEachRow() {
		return seatsInEachRow;
	}
	/**
	 * @param seatsInEachRow the seatsInEachRow to set
	 */
	public void setSeatsInEachRow(Integer seatsInEachRow) {
		this.seatsInEachRow = seatsInEachRow;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj == this)
			return false;
		if (!(obj instanceof Seat))
			return false;
		Seat level = (Seat) obj;
		if (level.getLevel().getLevelId() == this.levelId) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
