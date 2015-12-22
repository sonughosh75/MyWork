package org.walmart.ticket.interfaces.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.service.TicketService;
import org.walmart.ticket.domain.value.SeatHoldRequest;

@RestController
public class TicketController {

	@Inject
	private TicketService ticketService;

	@RequestMapping(value = "v1/tickets/all", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> findAllSeats() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Seat> seatsList = ticketService.findAllSeats();
		result.put("Seats", seatsList);
		return result;
	}

	@RequestMapping(value = "v1/tickets/level/{levelId}", method = RequestMethod.GET)
	public @ResponseBody List<Seat> findSeatsByLevel(
			@PathVariable("levelId") String levelId) {
		return ticketService.findAllSeatsByLevel(Integer.parseInt(levelId));
	}

	@RequestMapping(value = "v1/tickets/available", method = RequestMethod.GET)
	public List<Seat> findAllAvailableSeats() {
		return ticketService.findAllAvailableSeats();
	}

	@RequestMapping(value = "v1/tickets/available/{levelId}", method = RequestMethod.GET)
	public List<Seat> findAllAvailableSeatByLevel(@PathVariable String levelId) {
		return ticketService.findAllAvailableSeatsByLevelId(Integer
				.parseInt(levelId));
	}

	/**
	 * Calls a ExecutorService to submit threads to the pool
	 * If the thread is not completed within 2 minutes, then the held seats are not reserved.
	 * @param seatHoldRequest
	 * @return
	 */
	@RequestMapping(value = "v1/tickets/hold/{seatHold}", method = RequestMethod.POST)
	public @ResponseBody String holdSeats(
			@RequestBody final SeatHoldRequest seatHoldRequest) {
		SeatHold result = null;
		//Creates a pool of 10 threads
		final ExecutorService executorService = Executors
				.newFixedThreadPool(10);
		// submits the task of holding seats
		Future<SeatHold> futureHoldSeats = executorService
				.submit(new Callable<SeatHold>() {
					public SeatHold call() {
						return ticketService.findAndHoldSeats(
								seatHoldRequest.getNumSeats(),
								seatHoldRequest.getMinLevel(),
								seatHoldRequest.getMaxLevel(),
								seatHoldRequest.getCustomerEmail()); // getting values from the request
					}
				});
		try {
				// the tickets will be held for 2 minutes i.e. 120 seconds 
				result = futureHoldSeats.get(120, TimeUnit.SECONDS);
				// Do reserve the seats which are held
				ticketService.reserveSeats(result.getSeatHoldId(), seatHoldRequest.getCustomerEmail());
		} catch (TimeoutException | ExecutionException |InterruptedException ee) {
				// cancel the task
				futureHoldSeats.cancel(true);
		}finally {
			executorService.shutdown();
		}
		return "SUCCESS";
	}

	@RequestMapping(value = "v1/tickets/reserve", method = RequestMethod.POST)
	public String reserveSeats() {
		return null;
	}
}
