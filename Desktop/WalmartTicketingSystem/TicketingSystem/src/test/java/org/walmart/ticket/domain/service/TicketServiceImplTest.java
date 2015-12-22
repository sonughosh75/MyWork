package org.walmart.ticket.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.repository.SeatRepository;
import org.walmart.ticket.error.TicketRuntimeExeption;
import org.walmart.ticket.utils.UnitTestHelper;

public class TicketServiceImplTest {

	private TicketService ticketService;

	@Mock
	private SeatRepository seatRepo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(TicketServiceImplTest.class);
		seatRepo = Mockito.mock(SeatRepository.class);
		ticketService = new TicketServiceImpl();
		ticketService.setSeatRepository(seatRepo);
	}

	public void testNumSeatsAvailable() {
		int venueLevel = 1;
		List<Seat> availableSeatList = UnitTestHelper.createAvailableSeatList(venueLevel);
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class)))
				.thenReturn(availableSeatList);
		Integer countOfSeats = ticketService.numSeatsAvailable(venueLevel);
		assertTrue(countOfSeats > 0);
		assertEquals(countOfSeats, Integer.valueOf(4));

	}

	@Test(expected = TicketRuntimeExeption.class)
	public void testFindAndHoldSeats_InsufficientSeatsException() {
		int numSeats = 20;
		int minLevel = 1;
		int maxLevel = 0;
		String customerEmail = "ghosh1.sourav@gmail.com";
		
		List<Seat> availableSeatListLevel1 = UnitTestHelper.createAvailableSeatList(minLevel);
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class))).thenReturn(availableSeatListLevel1);
		SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		assertNotNull(seatHold);
	}
	
	@Test
	public void testFindAndHoldSeatsMinLevel() {
		int numSeats = 2;
		int minLevel = 1;
		int maxLevel = 0;
		String customerEmail = "ghosh1.sourav@gmail.com";
		
		List<Seat> availableSeatListLevel1 = UnitTestHelper.createAvailableSeatList(minLevel);
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class))).thenReturn(availableSeatListLevel1);
		SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		
		assertNotNull(seatHold);
		assertEquals(seatHold.getSeats().size(), 2);
		assertEquals(seatHold.getLevelId(),Integer.valueOf(minLevel));
	}
	
	@Test
	public void testFindAndHoldSeatsMaxLevel() {
		int numSeats = 2;
		int minLevel = 0;
		int maxLevel = 2;
		String customerEmail = "ghosh1.sourav@gmail.com";
		
		List<Seat> availableSeatListLevel1 = UnitTestHelper.createAvailableSeatList(maxLevel);
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class))).thenReturn(availableSeatListLevel1);
		SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		
		assertNotNull(seatHold);
		assertEquals(seatHold.getSeats().size(), 2);
		assertEquals(seatHold.getLevelId(),Integer.valueOf(maxLevel));
	}
	
	@Test
	public void testFindAndHoldSeatsMinAndMaxLevel() {
		int numSeats = 2;
		int minLevel = 1;
		int maxLevel = 2;
		String customerEmail = "ghosh1.sourav@gmail.com";
		
		List<Seat> availableSeatListLevel2 = UnitTestHelper.createAvailableSeatList(maxLevel);
		List<Seat> availableSeatListLevel1 = UnitTestHelper.createAvailableSeatList(minLevel);
		List<Seat> availableSeats = new ArrayList<Seat>(availableSeatListLevel1);
		availableSeats.addAll(availableSeatListLevel2);
		
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class))).thenReturn(availableSeats);
		
		SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		
		assertNotNull(seatHold);
		assertEquals(seatHold.getSeats().size(), 2);
		assertEquals(seatHold.getLevelId(),Integer.valueOf(maxLevel));
	}
	
	@Test
	public void testFindAndHoldSeatsMinAndMaxLevel_MultiLevelHold() {
		int numSeats = 10;
		int minLevel = 1;
		int maxLevel = 2;
		String customerEmail = "ghosh1.sourav@gmail.com";
		
		List<Seat> availableSeats = UnitTestHelper.createAvailableSeatList();
		
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class))).thenReturn(availableSeats);
		
		SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		
		assertNotNull(seatHold);
		assertEquals(seatHold.getSeats().size(), 6);
		assertEquals(seatHold.getLevelId(),Integer.valueOf(maxLevel));
	}

	@Test
	public void testReserveSeats() {
		String seatHoldId = UnitTestHelper.createSeatHold().getSeatHoldId();
		when(seatRepo.reserveSeat(Mockito.anyString(), Mockito.any(String.class))).thenReturn("SUCCESS");
		String messages = ticketService.reserveSeats(seatHoldId, "a@a.com");
		assertNotNull(messages);
		assertEquals(messages, "SUCCESS");
	}

	@Test
	public void testFindAllSeats() {
		int venueLevel = 1;
		List<Seat> allSeatList = UnitTestHelper.createAvailableSeatList(venueLevel);
		when(seatRepo.findAllSeats())
				.thenReturn(allSeatList);
		List<Seat> seatList = ticketService.findAllSeats();
		assertNotNull(seatList);
		assertEquals(seatList.size(), 4);
	}

	@Test
	public void testFindAllAvailableSeatsByLevelId() {
		int venueLevel = 2;
		List<Seat> availableSeatList = UnitTestHelper.createAvailableSeatList(venueLevel);
		when(seatRepo.findAllAvailableSeatsByLevel(Mockito.any(Integer.class)))
				.thenReturn(availableSeatList);
		List<Seat> availableSeatsList = ticketService.findAllAvailableSeatsByLevelId(venueLevel);
		assertEquals(availableSeatsList.size(), 4);
	}

	@Test
	public void testFindAllSeatsByLevel() {
		int venueLevel =1;
		List<Seat> availableSeatList = UnitTestHelper.createAvailableSeatList(venueLevel);
		when(seatRepo.findSeatByLevelId(Mockito.any(Integer.class)))
				.thenReturn(availableSeatList);
		List<Seat> availableSeatsList = ticketService.findAllSeatsByLevel(venueLevel);
		assertEquals(availableSeatsList.size(), 4);
	}

	@Test
	public void testFindAllAvailableSeats() {
		int venueLevel =1;
		List<Seat> availableSeatList = UnitTestHelper.createAvailableSeatList(venueLevel);
		when(seatRepo.findAllAvailableSeats())
				.thenReturn(availableSeatList);
		List<Seat> availableSeatsList = ticketService.findAllAvailableSeats();
		assertEquals(availableSeatsList.size(), 4);
	}

}
