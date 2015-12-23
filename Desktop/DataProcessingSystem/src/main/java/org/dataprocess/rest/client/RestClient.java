package org.dataprocess.rest.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class RestClient {
	public static void main(String[] args) throws ClientProtocolException,
			IOException, URISyntaxException {
		RestClient.addCategory();
		RestClient.getCategories();
		RestClient.removeCategory();
	}

	private static void getCategories() throws MalformedURLException,
			IOException, UnsupportedEncodingException {
		String sAddress = "http://localhost:8081/data-web/doj/category/categoryData";
		StringBuilder sb = new StringBuilder();

		URL url = new URL(sAddress);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					con.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					bufferedInputStream, "UTF-8"));
			String line = null;

			while ((line = reader.readLine()) != null)
				sb.append(line);

			bufferedInputStream.close();
		}
		System.out.println(sb.toString());
	}

	public static void addCategory() throws MalformedURLException, IOException {
			
			HttpClient httpClient = new DefaultHttpClient();
			String query = String.format("param1=%s",
					URLEncoder.encode("Animal", "UTF-8"));
			String sAddress = "http://localhost:8081/data-web/doj/category/addCategory?"
					+ query;
			HttpPost postRequest = new HttpPost(sAddress);

			StringEntity input = new StringEntity("Animal");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
	}

	public static void removeCategory() throws MalformedURLException,
			IOException, URISyntaxException {
		HttpClient httpClient = new DefaultHttpClient();
		String query = String.format("param1=%s",
				URLEncoder.encode("ANIMAL", "UTF-8"));
		String sAddress = "http://localhost:8081/data-web/doj/category/removeCategory?"
				+ query;
		HttpDelete deleteRequest = new HttpDelete(sAddress);
		deleteRequest.setURI(new URI(sAddress));
		StringEntity input = new StringEntity("Animal");
		input.setContentType("application/json");

		HttpResponse response = httpClient.execute(deleteRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
	}

}
