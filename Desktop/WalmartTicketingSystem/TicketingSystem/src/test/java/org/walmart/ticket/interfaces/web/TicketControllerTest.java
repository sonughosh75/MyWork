package org.walmart.ticket.interfaces.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.walmart.ticket.config.CoreSpringWebConfig;
import org.walmart.ticket.utils.IntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { CoreSpringWebConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@Category(IntegrationTest.class)
public class TicketControllerTest {

	@Inject
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testFindAllSeats() throws Exception {
		mockMvc.perform(
				get("/v1/tickets/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Seats", hasSize(8)));
	}

	@Test
	public void testFindSeatsByLevel() throws Exception {
		// ResultActions response = mockMvc.perform(
		// get("v1/tickets/level/"+"2").contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void testFindAllAvailableSeats() throws Exception {
		ResultActions response = mockMvc.perform(
				get("v1/tickets/available").accept(
						MediaType.APPLICATION_JSON));//.andExpect(status().isOk());
		System.out.println(response);
	}
	//
	// @Test
	// public void testFindAllAvailableSeatByLevel() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testHoldSeats() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testReserveSeats() {
	// fail("Not yet implemented");
	// }

}
