package org.walmart.ticket.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;

public class UnitTestHelper {

	public static SeatHold createSeatHold() {

		SeatLevel seatLevel = new SeatLevel(1);
		Set<Seat> seats = createSeatSet(seatLevel, true, false);

		SeatHold seatHold = createSeatHold(seatLevel, "1", "a@a.com", seats);

		return seatHold;
	}

	private static Set<Seat> createSeatSet(SeatLevel seatLevel, Boolean isReserved, Boolean isSeatOnHold) {
		Set<Seat> seats = new HashSet<Seat>();
		Seat seat1 = UnitTestHelper.createSeat(isReserved, isSeatOnHold, seatLevel,
				new Random().nextInt(), String.valueOf(new Random().nextInt()));
		Seat seat2 = UnitTestHelper.createSeat(isReserved, isSeatOnHold, seatLevel,
				new Random().nextInt(), String.valueOf(new Random().nextInt()));
		seats.add(seat1);
		seats.add(seat2);
		return seats;
	}

	private static SeatHold createSeatHold(SeatLevel seatLevel,
			String seatHoldId, String email, Set<Seat> seats) {
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomerEmail(email);
		seatHold.setLevelId(seatLevel.getLevelId());
		seatHold.setSeatHoldId(seatHoldId);
		seatHold.setSeats(seats);
		return seatHold;
	}

	public static Map<String, SeatHold> createSeatHoldMap() {
		SeatLevel seatLevel = new SeatLevel(1);
		Set<Seat> seats = createSeatSet(seatLevel, true, false);
		SeatHold seatHold1 = createSeatHold(seatLevel, "111", "a@a.com", seats);
		SeatHold seatHold2 = createSeatHold(seatLevel, "222", "a@a.com", seats);
		final Map<String, SeatHold> seatHoldMap = new ConcurrentHashMap<String, SeatHold>();

		seatHoldMap.put("111", seatHold1);
		seatHoldMap.put("222", seatHold2);
		return seatHoldMap;
	}

	private static Seat createSeat(Boolean isReserved, Boolean isSeatOnHold,
			SeatLevel level, Integer rowNumber, String seatNumber) {
		Seat seat = new Seat();
		seat.setIsReserved(isReserved);
		seat.setIsSeatOnHold(isSeatOnHold);
		seat.setLevel(level);
		seat.setRowNumber(rowNumber);
		seat.setSeatNumber(seatNumber);

		return seat;
	}
	
	public static List<Seat> createAvailableSeatList(Integer levelId) {
		SeatLevel seatLevel1 = new SeatLevel(levelId);
		Set<Seat> availableSeatSet1 = createSeatSet(seatLevel1, false, false);
		SeatLevel seatLevel2 = new SeatLevel(levelId);
		Set<Seat> availableSeatSet2 = createSeatSet(seatLevel2, true, false);
		List<Seat> availableSeatList1 = new ArrayList<Seat>(availableSeatSet1);
		List<Seat> availableSeatList2 = new ArrayList<Seat>(availableSeatSet2);
		
		List<Seat> availableList = new ArrayList<Seat>(availableSeatList1);
		availableList.addAll(availableSeatList2);
		
		return availableList;
		
	}
	
	public static List<Seat> createAvailableSeatList() {
		Seat availableSeat1 = createSeat(false, false, new SeatLevel(1), 1, "1A");
		Seat availableSeat2 = createSeat(false, false, new SeatLevel(1), 1, "2A");
		Seat availableSeat3 = createSeat(false, false, new SeatLevel(1), 1, "3A");
		Seat availableSeat4 = createSeat(false, false, new SeatLevel(1), 1, "4A");
		
		Seat availableSeat5 = createSeat(false, false, new SeatLevel(2), 1, "1A");
		Seat availableSeat6 = createSeat(false, false, new SeatLevel(2), 1, "2A");
		Seat availableSeat7= createSeat(true, false, new SeatLevel(2), 1, "3A");
		Seat availableSeat8 = createSeat(false, true, new SeatLevel(2), 1, "4A");
		
		Set<Seat> seats = new HashSet<Seat>();
		seats.add(availableSeat1);seats.add(availableSeat2);seats.add(availableSeat3);seats.add(availableSeat4);
		seats.add(availableSeat5);seats.add(availableSeat6);seats.add(availableSeat7);seats.add(availableSeat8);
		return new ArrayList<Seat>(seats);
	}
}
