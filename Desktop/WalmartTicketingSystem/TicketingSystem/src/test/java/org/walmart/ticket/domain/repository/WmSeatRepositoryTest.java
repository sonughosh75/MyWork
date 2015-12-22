package org.walmart.ticket.domain.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.walmart.ticket.domain.aggregate.SeatAggregrate;
import org.walmart.ticket.domain.entity.Seat;
import org.walmart.ticket.domain.entity.SeatHold;
import org.walmart.ticket.domain.entity.SeatLevel;
import org.walmart.ticket.utils.UnitTestHelper;

@RunWith(MockitoJUnitRunner.class)
public class WmSeatRepositoryTest {

	private WmSeatRepository repo;

	private SeatAggregrate seatAggregrate;

	@Mock
	private Map<String, SeatHold> seatHoldMap;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(WmSeatRepositoryTest.class);
		seatAggregrate = new SeatAggregrate();
		seatAggregrate.setLevelMap(setSeatLevelInfo());
		seatAggregrate.setSeatHoldMap(UnitTestHelper.createSeatHoldMap());
		seatAggregrate.setSeatMap(returnSeatMap());
		repo = new WmSeatRepository();
		repo.setSeatAggregrate(seatAggregrate);
	
	}

	@Test
	public void testReserveSeat() {
		String customerEmail = "a@a.com";
		seatHoldMap = UnitTestHelper.createSeatHoldMap();
		SeatHold seatHold = UnitTestHelper.createSeatHold();
		repo.findAndHoldSeats(seatHold);
		String successCode = repo.reserveSeat(seatHold.getSeatHoldId(),
				customerEmail);
		assertNotNull(successCode);
	}

	@Test
	public void testFindAllSeats() {
		List<Seat> allSeats = repo.findAllSeats();
		assertNotNull(allSeats);
		assertTrue(allSeats.size() > 1);
		assertEquals(allSeats.size(),6);
	}

	@Test
	public void testFindSeatBySeatNumber() {
		Map<String, Seat> seatMap = returnSeatMap();
		//when(seatAggregrate.getSeatMap()).thenReturn(seatMap);
		Seat seat = repo.findSeatBySeatNumber("1A");
		assertNotNull(seat);
		assertEquals(seat.getSeatNumber(), "1A");
		assertEquals(seat.getIsReserved(), Boolean.FALSE);
		assertEquals(seat.getIsSeatOnHold(), Boolean.TRUE);
	}

	@Test
	public void testFindSeatByLevelId() {
		List<Seat> seatList = repo.findSeatByLevelId(1);
		assertNotNull(seatList);
		assertTrue(seatList.size() > 0);
		assertEquals(seatList.size(), 1250);
		
		seatList = repo.findSeatByLevelId(2);
		assertEquals(seatList.size(),2000);
		
		seatList = repo.findSeatByLevelId(3);
		assertEquals(seatList.size(),1500);
		
		seatList = repo.findSeatByLevelId(4);
		assertEquals(seatList.size(),1500);
	}

	@Test
	public void testExistsTrue() {
		Map<String, Seat> seatMap = returnSeatMap();
		Boolean isExists = repo.exists("1B");
		assertTrue(isExists);
	}

	@Test
	public void testExistsFalse() {
		Map<String, Seat> seatMap = returnSeatMap();
	//	when(seatAggregrate.getSeatMap()).thenReturn(seatMap);
		Boolean isExists = repo.exists("WRONG");
		assertFalse(isExists);
	}

	@Test
	public void testSeatHold() {
		Map<String, Seat> seatMap = returnSeatMap();
	//	when(seatAggregrate.getSeatMap()).thenReturn(seatMap);
		assertEquals(seatMap.size(),6);
	}

	@Test
	public void testFindAllAvailableSeats_NotAvailable() {
		Map<String, Seat> seatMap = returnSeatMap();
		List<Seat> allAvailableSeats = repo.findAllAvailableSeats();
		assertNotNull(allAvailableSeats);
		assertEquals(allAvailableSeats.size(), 0);

	}

	@Test
	public void testFindAllAvailableSeats_Available() {
//		Map<String, Seat> seatMap = returnSeatMap();
//		Seat seat1 = createSeat(false, false, new SeatLevel(1), 1, "1A");
//		seatMap.put("1A", seat1);

		List<Seat> allAvailableSeats = repo.findAllAvailableSeats();
		assertNotNull(allAvailableSeats);
		assertEquals(allAvailableSeats.size(), 0);

	}

	@Test
	public void testFindAllAvailableSeatsByLevel() {
		Map<String, Seat> seatMap = returnAvailableSeatMap();
	//	when(seatAggregrate.getSeatMap()).thenReturn(seatMap);
		List<Seat> allAvailableSeatsByLevel = repo
				.findAllAvailableSeatsByLevel(1);
		assertNotNull(allAvailableSeatsByLevel);
		assertEquals(allAvailableSeatsByLevel.size(), 0);
	}

	@Test
	public void testFindAndHoldSeats() {
		Map<String, SeatHold> seatHoldMap =UnitTestHelper.createSeatHoldMap();
	//	when(seatAggregrate.getSeatHoldMap()).thenReturn(seatHoldMap);
		Map<String, SeatHold> seatHold = repo.findAndHoldSeats(createSeatHold());
		assertNotNull(seatHold);
		assertTrue(seatHold.size()>0);
		assertEquals(seatHold.size(),3);
	}

	private static Map<String, Seat> createSeatMap(Seat... seat) {
		Map<String, Seat> seatMap = new ConcurrentHashMap<String, Seat>();
		for (int i = 0; i < seat.length; i++) {
			seatMap.put(seat[i].getSeatNumber(), seat[i]);
		}
		return seatMap;
	}

	private static SeatHold createSeatHold() {
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomerEmail("customerEmail");
		seatHold.setLevelId(1);
		seatHold.setSeatHoldId("1");

		Set<Seat> seats = new HashSet<Seat>();
		seats.add(createSeat(false, false, new SeatLevel(1), 1, "1A"));
		seats.add(createSeat(false, false, new SeatLevel(1), 1, "1B"));
		seats.add(createSeat(false, true, new SeatLevel(2), 1, "2A"));
		seats.add(createSeat(false, false, new SeatLevel(2), 1, "2B"));

		seatHold.setSeats(seats);
		return seatHold;
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
		seatLevel2.setSeats(createSeats(seatLevel2,rows, seatsInRow));
		levelMap.put(2, seatLevel2);
		
		rows = 15;
		seatsInRow = 100;
		SeatLevel seatLevel3 = createSeatLevel(3, "Balcony 1",new Double("50.00"),15,100);
		seatLevel3.setSeats(createSeats(seatLevel3,rows, seatsInRow));
		levelMap.put(3, seatLevel3);
		
		rows = 15;
		seatsInRow = 100;
		SeatLevel seatLevel4 = createSeatLevel(4, "Balcony 2",new Double("40.00"),15,100);
		seatLevel4.setSeats(createSeats(seatLevel4, rows, seatsInRow));
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

		Seat seat5 = createSeat(true, false, new SeatLevel(2), 1, "2A");
		Seat seat6 = createSeat(true, false, new SeatLevel(2), 1, "2B");

		Map<String, Seat> seatMap = createSeatMap(seat1, seat2, seat3, seat4,
				seat5, seat6);
		return seatMap;
	}

	private static Map<String, Seat> returnAvailableSeatMap() {
		Seat seat1 = createSeat(false, false, new SeatLevel(1), 1, "1A");
		Seat seat2 = createSeat(false, false, new SeatLevel(1), 1, "1B");
		Seat seat3 = createSeat(false, false, new SeatLevel(1), 1, "1C");
		Seat seat4 = createSeat(true, false, new SeatLevel(1), 1, "1D");

		Seat seat5 = createSeat(false, true, new SeatLevel(2), 1, "2A");
		Seat seat6 = createSeat(false, true, new SeatLevel(2), 1, "2B");

		Map<String, Seat> seatMap = createSeatMap(seat1, seat2, seat3, seat4,
				seat5, seat6);
		return seatMap;
	}

}
