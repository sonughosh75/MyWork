package org.walmart.ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.walmart.ticket.domain.aggregate.SeatAggregrate;
import org.walmart.ticket.domain.repository.WmSeatRepository;
import org.walmart.ticket.domain.repository.SeatRepository;
import org.walmart.ticket.domain.service.TicketService;
import org.walmart.ticket.domain.service.TicketServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.walmart.ticket")
@WebAppConfiguration
public class CoreSpringWebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public TicketService mockTicketService() {
		return new TicketServiceImpl();
	}
	
	@Bean
	public SeatRepository mockSeatRepository() {
		return new WmSeatRepository();
	}
	
	@Bean
	public SeatAggregrate seatAggregrate() {
		return new SeatAggregrate();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter converter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper());
		return converter;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}

}
