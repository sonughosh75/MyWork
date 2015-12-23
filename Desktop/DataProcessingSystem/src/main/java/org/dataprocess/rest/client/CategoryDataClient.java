package org.dataprocess.rest.client;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dataprocess.domain.Category;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Named
public class CategoryDataClient {

	private static final String CATEGORY_DATA_URL_V1 = "http://localhost:8080/v1/categories";

	@Inject
	private RestTemplate restTemplate;

	@Inject
	private CategoryRequestBuilder requestBuilder;

	public ResponseEntity<List> getCategoryData(Category[] categoryData) {
		URI uri = requestBuilder.builderUri(categoryData);
		ResponseEntity<List> responseList = executeExchange(uri);
		
		return responseList;
	}

	private ResponseEntity<List> executeExchange(URI uri) {
		ResponseEntity<List> responseList = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(null,
				requestBuilder.buildHttpHeaders()), List.class);
		return responseList;
	}
}
