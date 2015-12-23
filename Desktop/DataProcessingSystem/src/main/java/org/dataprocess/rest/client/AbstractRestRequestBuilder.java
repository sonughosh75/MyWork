package org.dataprocess.rest.client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.springframework.http.HttpHeaders;

public abstract class AbstractRestRequestBuilder {
	private static final String DEFAULT_ENCODE = "UTF-8";
	
	public String encode(String value){
		try{
			return URLEncoder.encode(value, DEFAULT_ENCODE);
		}catch(UnsupportedEncodingException encodingException){
			throw new RuntimeException(encodingException);
		}
	}

}
