package org.walmart.ticket.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.walmart.ticket.domain.aggregate.SeatAggregrate;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;
import org.walmart.ticket.utils.UnitTestHelper;

public class WmSeatRepository implements SeatRepository {

	private SeatAggregrate seatAggregrate;

	public WmSeatRepository() {
		seatAggregrate = new SeatAggregrate();
		seatAggregrate.setLevelMap(setSeatLevelInfo());
		seatAggregrate.setSeatHoldMap(UnitTestHelper.createSeatHoldMap());
		seatAggregrate.setSeatMap(returnSeatMap());
	}

	/***
	 * @param String
	 *            seatHoldId
	 * @param String
	 *            customerEmail
	 * @return String
	 */
	public String reserveSeat(String seatHoldId, String customerEmail) {
		SeatHold seatHold = seatAggregrate.getSeatHoldMap().get(seatHoldId);
		Set<Seat> seats = seatHold.getSeats();
		for (Seat seat : seats) {
			seatAggregrate.getSeatMap().put(seat.getSeatNumber(), seat);
		}
		return "SUCCESS";
	}

	/***
	 * 
	 */
	public List<Seat> findAllSeats() {
		return new ArrayList<Seat>(seatAggregrate.getSeatMap().values());
	}

	public Seat findSeatBySeatNumber(String seatNumber) {
		return seatAggregrate.getSeatMap().get(seatNumber);
	}

	/**
	 * Method that return seats for specified seat level, available, reserved
	 * and held
	 * 
	 * @param Integer
	 *            level Id
	 * @return list of seats
	 */
	public List<Seat> findSeatByLevelId(Integer levelId) {
		List<Seat> matchingSeats = null;
		Map<Integer, SeatLevel> seatLevelInfo = fetchSeatLevelInfo();
		SeatLevel levelInfo = seatLevelInfo.get(levelId);
		if (levelInfo != null) {
			matchingSeats = new ArrayList<Seat>(levelInfo.getSeats());
		}
		return matchingSeats;
	}

	public Boolean exists(String seatNumber) {
		return seatAggregrate.getSeatMap().containsKey(seatNumber);
	}

	@Override
	public List<Seat> findAllAvailableSeats() {
		List<Seat> allSeats = this.findAllSeats();
		List<Seat> availableSeatsList = new ArrayList<Seat>();

		for (Seat seat : allSeats) {
			if (allSeats.contains(seat)) {
				availableSeatsList.add(seat);
			}
		}
		return availableSeatsList;
	}

	/**
	 * Method that returns all seat levels that are available for a particular
	 * seat level
	 * 
	 * @param Integer
	 *            level Id
	 * @return list of seats
	 */
	@Override
	public List<Seat> findAllAvailableSeatsByLevel(Integer levelId) {
		List<Seat> availableSeats = findAllAvailableSeats();
		List<Seat> resultList = new ArrayList<Seat>();
		for (Seat seat : availableSeats) {
			if (availableSeats.contains(seat.getLevel())) {
				resultList.add(seat);
			}
		}
		return resultList;
	}

	@Override
	public Map<String, SeatHold> findAndHoldSeats(SeatHold seatHold) {
		Map<String, SeatHold> seatHoldMap = getSeatAggregrate()
				.getSeatHoldMap();
		seatHoldMap.put(seatHold.getSeatHoldId(), seatHold);
		getSeatAggregrate().setSeatHoldMap(seatHoldMap);
		return seatHoldMap;
	}

	/**
	 * @return the seatAggregrate
	 */
	public SeatAggregrate getSeatAggregrate() {
		return seatAggregrate;
	}

	/**
	 * @param seatAggregrate
	 *            the seatAggregrate to set
	 */
	public void setSeatAggregrate(SeatAggregrate seatAggregrate) {
		this.seatAggregrate = seatAggregrate;
	}

	@Override
	public Map<Integer, SeatLevel> fetchSeatLevelInfo() {
		return seatAggregrate.getLevelMap();
	}

	private static Seat createSeat(Boolean isReserved, Boolean isHold,
			SeatLevel seatLevel, Integer rowNumber, String seatNumber) {
		Seat seat = new Seat();
		seat.setIsReserved(isReserved);
		seat.setIsSeatOnHold(isHold);
		seat.setLevel(seatLevel);
		seat.setRowNumber(rowNumber);
		seat.setSeatNumber(seatNumber);
		return seat;
	}
	
	public Map<Integer, SeatLevel> setSeatLevelInfo() {
		int rows = 25;
		int seatsInRow = 50;
		Map<Integer, SeatLevel> levelMap = new LinkedHashMap<Integer, SeatLevel>();
		SeatLevel seatLevel1 = createSeatLevel(1, "Orchestra",new Double("100.00"),25,50);
		seatLevel1.setSeats(createSeats(seatLevel1, rows, seatsInRow));
		levelMap.put(1, seatLevel1);
		
		rows = 20;
		seatsInRow = 100;
		SeatLevel seatLevel2 = createSeatLevel(2, "Main",new Double("75.00"),20,100);
		seatLevel1.setSeats(createSeats(seatLevel2,rows, seatsInRow));
		levelMap.put(2, seatLevel2);
		
		rows = 15;
		seatsInRow = 100;
		SeatLevel seatLevel3 = createSeatLevel(3, "Balcony 1",new Double("50.00"),15,100);
		seatLevel1.setSeats(createSeats(seatLevel3,rows, seatsInRow));
		levelMap.put(3, seatLevel3);
		
		rows = 15;
		seatsInRow = 100;
		SeatLevel seatLevel4 = createSeatLevel(4, "Balcony 2",new Double("40.00"),15,100);
		seatLevel1.setSeats(createSeats(seatLevel4, rows, seatsInRow));
		levelMap.put(4, seatLevel4);
		
		return levelMap;
	}
	
	private static SeatLevel createSeatLevel(int levelId, String name, Double price,
			int numOfRows, int seatsPerRow) {
		SeatLevel seatLevel = new SeatLevel( levelId,  name,  price,
				 numOfRows,  seatsPerRow);
		return seatLevel;
	}
	
	private static Set<Seat> createSeats(SeatLevel seatLevel, int rows, int seatsInRow) {
		Set<Seat> seats = new HashSet<Seat>();
		int seatArr[][] = new int[rows][seatsInRow];
		Map<Integer,String> rowName = createRowName();
		for(int i=0;i<rows ;i++) {
			for(int j=0;j<seatsInRow;j++) {
				seats.add(createSeat(false, false, seatLevel, i, rowName.get(i)));
			}
		}
		return seats;
	}
	
	private static Map<Integer, String> createRowName() {
		Map<Integer, String> rowNameMap= new HashMap<Integer, String>(); 
		rowNameMap.put(1, "A");
		rowNameMap.put(2, "B");
		rowNameMap.put(3, "C");
		rowNameMap.put(4, "D");
		return rowNameMap;
	}
	
	public static Map<String, Seat> returnSeatMap() {
		Seat seat1 = createSeat(false, true, new SeatLevel(1), 1, "1A");
		Seat seat2 = createSeat(false, true, new SeatLevel(1), 1, "1B");
		Seat seat3 = createSeat(true, false, new SeatLevel(1), 1, "1C");
		Seat seat4 = createSeat(true, false, new SeatLevel(1), 1, "1D");
		Seat seat8 = createSeat(false, false, new SeatLevel(1), 1, "1E"); //available

		Seat seat5 = createSeat(true, false, new SeatLevel(2), 1, "2A");
		Seat seat6 = createSeat(true, false, new SeatLevel(2), 1, "2B");
		Seat seat7 = createSeat(false, false, new SeatLevel(2), 1, "2C"); // available seat

		Map<String, Seat> seatMap = createSeatMap(seat1, seat2, seat3, seat4,
				seat5, seat6, seat7, seat8);
		return seatMap;
	}
	
	private static Map<String, Seat> createSeatMap(Seat... seat) {
		Map<String, Seat> seatMap = new ConcurrentHashMap<String, Seat>();
		for (int i = 0; i < seat.length; i++) {
			seatMap.put(seat[i].getSeatNumber(), seat[i]);
		}
		return seatMap;
	}
}
