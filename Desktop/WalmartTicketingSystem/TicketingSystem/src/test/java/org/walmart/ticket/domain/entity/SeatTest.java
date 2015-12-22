/**
 * 
 */
package org.walmart.ticket.domain.entity;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sourav
 *
 */
public class SeatTest {

	private Seat seat1;
	private Seat seat2;
	
	@Before
	public void setup() {
		seat1 = new Seat();
		seat1.setIsReserved(false);
		seat1.setIsSeatOnHold(false);
		
		seat2 = new Seat();
		seat2.setIsReserved(true);
		seat2.setIsSeatOnHold(false);
		
		
	}
	/**
	 * Test method for {@link org.walmart.ticket.domain.entity.Seat#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		boolean isEquals = seat1.equals(seat2);
		assertFalse(isEquals);
	}

}
