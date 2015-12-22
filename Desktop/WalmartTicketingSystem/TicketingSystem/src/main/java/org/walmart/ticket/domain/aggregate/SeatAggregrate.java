package org.walmart.ticket.domain.aggregate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;

public class SeatAggregrate {
	
	private Map<String, Seat> seatMap = new ConcurrentHashMap<String, Seat>();
	private Map<Integer, SeatLevel> levelMap = new ConcurrentHashMap<Integer, SeatLevel>();
	private Map<String, SeatHold> seatHoldMap = new ConcurrentHashMap<String, SeatHold>();
	
	/**
	 * @return the seatMap
	 */
	public Map<String, Seat> getSeatMap() {
		return seatMap;
	}
	/**
	 * @param seatMap the seatMap to set
	 */
	public void setSeatMap(Map<String, Seat> seatMap) {
		this.seatMap = seatMap;
	}
	/**
	 * @return the levelMap
	 */
	public Map<Integer, SeatLevel> getLevelMap() {
		return this.levelMap;
	}
	/**
	 * @param levelMap the levelMap to set
	 */
	public void setLevelMap(Map<Integer, SeatLevel> levelMap) {
		this.levelMap = levelMap;
	}
	/**
	 * @return the seatHoldMap
	 */
	public Map<String, SeatHold> getSeatHoldMap() {
		return seatHoldMap;
	}
	/**
	 * @param seatHoldMap the seatHoldMap to set
	 */
	public void setSeatHoldMap(Map<String, SeatHold> seatHoldMap) {
		this.seatHoldMap = seatHoldMap;
	}
}
