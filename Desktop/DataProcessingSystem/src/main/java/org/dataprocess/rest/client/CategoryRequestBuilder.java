package org.dataprocess.rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.dataprocess.domain.Category;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public class CategoryRequestBuilder {
	private final String CATEGORIES_URI = "http://localhost:8080/v1/categories";

	public HttpHeaders buildHttpHeaders(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept", "application/json;v=1");
		httpHeaders.add("Content-type", "application/json;v=1;charset=UTF-8");
		httpHeaders.add("IP-Address","http://localhost:8080/");
		
		return httpHeaders;
	}
	
	public URI builderUri(Category[] categoryData) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(CATEGORIES_URI);
		return createUri(uriBuilder.buildAndExpand((Object[])categoryData).toUriString());
	}
	
	public URI createUri(String value) {
		try{
			return new URI(value);
		}catch(URISyntaxException syntaxException) {
			throw new RuntimeException(syntaxException);
		}
	}
}
