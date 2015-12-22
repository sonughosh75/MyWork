package org.walmart.ticket.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;
import org.walmart.ticket.domain.repository.SeatRepository;

@Service
public interface TicketService {
	/**
	 * The number of seats in the requested level that are neither held nor
	 * reserved
	 *
	 * @param venueLevel
	 *            a numeric venue level identifier to limit the search
	 * @return the number of tickets available on the provided level
	 */
	int numSeatsAvailable(Integer... venueLevel);

	/**
	 * Find and hold the best available seats for a customer
	 *
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param minLevel
	 *            the minimum venue level
	 * @param maxLevel
	 *            the maximum venue level
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 *         information
	 */
	SeatHold findAndHoldSeats(int numSeats, Integer minLevel,
			Integer maxLevel, String customerEmail);

	/**
	 * Commit seats held for a specific customer
	 *
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code
	 */
	String reserveSeats(String seatHoldId, String customerEmail);
	
	List<Seat> findAllSeats();
	
	List<Seat> findAllAvailableSeatsByLevelId(Integer levelId);
	
	List<Seat> findAllSeatsByLevel(Integer levelId);

	List<Seat> findAllAvailableSeats();

	void setSeatRepository(SeatRepository seatRepo);
	
	Map<Integer, SeatLevel> getSeatLevelInfo();
}
