package org.walmart.ticket.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;
import org.walmart.ticket.domain.repository.SeatRepository;
import org.walmart.ticket.error.ErrorId;
import org.walmart.ticket.error.TicketRuntimeExeption;

public class TicketServiceImpl implements TicketService {

	@Inject
	private SeatRepository seatRepository;

	/**
	 * Method to determine the number of seats to hold for a specific customer
	 * This method accepts an optional seat Level argument
	 * and returns all seats if no arguments are specified 
	 * else returns the available seats for that level. 
	 * 
	 * @param Variable parameter venue level
	 * @return The number of seats available
	 */
	public int numSeatsAvailable(Integer... venueLevel) {
		List<Seat> seatList = venueLevel.length > 0 ? seatRepository
				.findAllAvailableSeatsByLevel(venueLevel[0]) : seatRepository
				.findAllSeats();
		return seatList.size();
	}

	/**
	 * Method that holds the specified number of seats in the provided seats levels
	 * @param int numSeats
	 * @param Minimum level that the user wants seats to hold
	 * @param Maximum level that the user wants seats to hold
	 * @param Customer email address
	 * @return SeatHold
	 */
	public SeatHold findAndHoldSeats(int numSeats, Integer minLevel,
			Integer maxLevel, String customerEmail) {
		SeatHold seatHold = null;
		if (minLevel > 0 && maxLevel <= 0) {
			seatHold = holdSeats(numSeats, minLevel, customerEmail);
		} else if (maxLevel > 0 && minLevel <= 0) {
			seatHold = holdSeats(numSeats, maxLevel, customerEmail);
		} else {
			seatHold = holdSeats(numSeats, minLevel, maxLevel, customerEmail);
		}
		seatRepository.findAndHoldSeats(seatHold);
		return seatHold;
	}
	
	/**
	 * Method to hold seats when both seat levels are passed
	 * @param numSeats
	 * @param minLevel
	 * @param maxLevel
	 * @param customerEmail
	 * @return
	 */
	private SeatHold holdSeats(int numSeats, Integer minLevel,
			Integer maxLevel, String customerEmail) {
		SeatHold seatHold = new SeatHold();
		// Get the available seats in the maxLevel first. i.e. best seats will
		// be reserved first
		List<Seat> availableSeatsMaxLevel = findAllAvailableSeatsByLevelId(maxLevel);
		List<Seat> availableSeatsMinLevel = findAllAvailableSeatsByLevelId(minLevel);

		// All available seats in the min and max levels
		List<Seat> availableSeats = new ArrayList<Seat>(availableSeatsMinLevel);
		availableSeats.addAll(availableSeatsMaxLevel);
		
		// Sorting the list in the descending order of the LevelId so that the higher level seats are booked first
		Collections.sort(availableSeats, new Comparator<Seat>() {
			@Override
			public int compare(Seat o1, Seat o2) {
				return o2.getLevel().getLevelId().compareTo(o1.getLevel().getLevelId());
			}
		});

		Set<Seat> seats = new HashSet<Seat>();
		// if the user has specified the min and max level, then the seats in
		// the max level will be hold first
		// number of seats requested is greater than available in that level, exception is thrown

		if (numSeats > availableSeats.size()) {
			throw new TicketRuntimeExeption(String.format(
					"%d seats are not available.", numSeats), 100,
					ErrorId.INSUFFICIENT_SEATS_ERROR);
		} else {
			for(int i=0;i<numSeats;i++) {
				Seat seat = availableSeats.get(i);
				seats.add(seat);
			}
			seatHold = populateSeatHold(maxLevel, customerEmail, seats);
		}
		return seatHold;
	}

	/***
	 * Method to hold seats when only the minimum seat level is specified
	 * @param numSeats
	 * @param minLevel
	 * @param customerEmail
	 * @return SeatHold
	 */
	private SeatHold holdSeats(int numSeats, Integer minLevel,
			String customerEmail) {
		SeatHold seatHold = null;
		List<Seat> availableSeats = findAllAvailableSeatsByLevelId(minLevel);
		// sufficient seats are not available to be reserved
		if (numSeats > availableSeats.size()) {
			throw new TicketRuntimeExeption(String.format(
					"%d seats are not available.", numSeats), 100,
					ErrorId.INSUFFICIENT_SEATS_ERROR);
		} else {
			// hold the seats
			seatHold = holdAvailableSeats(numSeats, minLevel, customerEmail,
					availableSeats);
		}
		return seatHold;
	}

	/***
	 * Method to hold seats when only the maximum seat level is specified
	 * @param numSeats
	 * @param minLevel
	 * @param customerEmail
	 * @param availableSeats
	 * @return
	 */
	private SeatHold holdAvailableSeats(int numSeats, Integer maxLevel,
			String customerEmail, List<Seat> availableSeats) {
		Set<Seat> seatSet = new HashSet<Seat>();
		for(int i=0;i<numSeats;i++){
			Seat seat = availableSeats.get(i);
			seatSet.add(seat);
		}
		SeatHold seatHold = populateSeatHold(maxLevel, customerEmail, seatSet);
		return seatHold;
	}

	/***
	 * Method that populates the Seat Hold to be returned
	 * @param minLevel
	 * @param customerEmail
	 * @param seats
	 * @return SeatHold
	 */
	private SeatHold populateSeatHold(Integer minLevel, String customerEmail,
			Set<Seat> seats) {
		SeatHold seatHold = new SeatHold();
		seatHold.setSeatHoldId(UUID.randomUUID().toString());
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setLevelId(minLevel);
		seatHold.setSeats(seats);
		return seatHold;
	}

	/**
	 * @param String seatHoldId
	 * @param String customerEmail
	 * @return String
	 */
	public String reserveSeats(String seatHoldId, String customerEmail) {
		return seatRepository.reserveSeat(seatHoldId, customerEmail);
	}

	public List<Seat> findAllSeats() {
		return seatRepository.findAllSeats();
	}

	@Override
	public List<Seat> findAllAvailableSeatsByLevelId(Integer levelId) {
		return seatRepository.findAllAvailableSeatsByLevel(levelId);
	}

	@Override
	public List<Seat> findAllSeatsByLevel(Integer levelId) {
		return seatRepository.findSeatByLevelId(levelId);
	}

	@Override
	public List<Seat> findAllAvailableSeats() {
		return seatRepository.findAllAvailableSeats();
	}

	/**
	 * @return the seatRepository
	 */
	public SeatRepository getSeatRepository() {
		return seatRepository;
	}

	/**
	 * @param seatRepository
	 *            the seatRepository to set
	 */
	public void setSeatRepository(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

	@Override
	public Map<Integer, SeatLevel> getSeatLevelInfo() {
		return seatRepository.fetchSeatLevelInfo();
	}
}
