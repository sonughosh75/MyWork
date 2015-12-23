package org.dataprocess.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dataprocess.domain.Category;
import org.dataprocess.service.DataProcessingServiceImpl;

import com.google.gson.Gson;

@Named
@Path("/category")
public class CategoryRestController {
	
	private DataProcessingServiceImpl service = new DataProcessingServiceImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/categoryData")
	public Response getAll() {
		Map<List<String>,TreeMap<String, Integer>> returnValue = service.cleanup(createDataStub());
		Gson gson = new Gson();
		String jsonString = gson.toJson(returnValue);
		Response resp =  Response.status(200).entity(jsonString).build();
		return resp;
	}
	private Category[] createDataStub() {

		Category[] categoryArr = new Category[] {
				new Category("PERSON", "Bob Jones"),
				new Category("PLACE", "Washington"),
				new Category("PERSON", "Mary"),
				new Category("COMPUTER", "Mac"),
				new Category("PERSON", "Bob Jones"),
				new Category("OTHER", "Tree"), new Category("ANIMAL", "Dog"),
				new Category("PLACE", "Texas"), new Category("FOOD", "Steak"),
				new Category("ANIMAL", "Cat"), new Category("PERSON", "Mac") };
		return categoryArr;
	}
	
	@POST
	@Path("/addCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(String categoryName){
		List<String> categoryList = service.addCategory(categoryName);
		Gson gson = new Gson();
		String jsonString = gson.toJson(categoryList);
		return Response.status(201).entity(jsonString).build();
	}
	
	@DELETE
	@Path("/removeCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeCategory(String categoryName){
		List<String> categoryList = service.removeCategory(categoryName);
		Gson gson = new Gson();
		String jsonString = gson.toJson(categoryList);
		return Response.status(200).entity(jsonString).build();
	}
}
