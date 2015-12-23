package org.dataprocess.rest.client;

import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.dataprocess.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDataClientTest {

	@Mock
	private RestTemplate mockRestemplate;

	@Mock
	private CategoryRequestBuilder mockRequestBuilder;
	
	private CategoryDataClient client;

	private HttpHeaders httpHeaders;

	private List rawResponseList;

	public URI uri;

	@Before
	public void setup() throws URISyntaxException {
		MockitoAnnotations.initMocks(CategoryDataClientTest.class);
		httpHeaders = new HttpHeaders();
		uri = new URI("sample.com");
		client = new CategoryDataClient();
		mockRequestBuilder = Mockito.mock(CategoryRequestBuilder.class);
		ReflectionTestUtils.setField(client, "requestBuilder", mockRequestBuilder);
		ReflectionTestUtils.setField(client, "restTemplate", mockRestemplate);
	}

	@Test
	public void testGetCategoryData() {
		ResponseEntity<List> rawResponse = new ResponseEntity<List>(
				rawResponseList, HttpStatus.OK);
		when(mockRequestBuilder.buildHttpHeaders()).thenReturn(httpHeaders);
		when(mockRequestBuilder.builderUri(Mockito.any(Category[].class)))
				.thenReturn(uri);
		ResponseEntity<List> responseEntity = client.getCategoryData(createDataStub());
		
		System.out.println(responseEntity);
	}
	
	private Category[] createDataStub() {

		Category[] categoryArr = new Category[] {
				new Category("PERSON", "BobJones"),
				new Category("PLACE", "Washington"),
				new Category("PERSON", "Mary"),
				new Category("COMPUTER", "Mac"),
				new Category("PERSON", "BobJones"),
				new Category("OTHER", "Tree"), new Category("ANIMAL", "Dog"),
				new Category("PLACE", "Texas"), new Category("FOOD", "Steak"),
				new Category("ANIMAL", "Cat"), new Category("PERSON", "Mac") };
		return categoryArr;
	}

}
