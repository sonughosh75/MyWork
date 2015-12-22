package org.walmart.ticket.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;

@Repository
public interface SeatRepository {
	String reserveSeat(String seatHoldId, String customerEmail);
	List<Seat> findAllSeats();
	List<Seat> findSeatByLevelId(Integer levelId);
	List<Seat> findAllAvailableSeats();
	List<Seat> findAllAvailableSeatsByLevel(Integer level);
	Map<String, SeatHold> findAndHoldSeats(SeatHold seatHold);
	Map<Integer, SeatLevel> fetchSeatLevelInfo();
}
