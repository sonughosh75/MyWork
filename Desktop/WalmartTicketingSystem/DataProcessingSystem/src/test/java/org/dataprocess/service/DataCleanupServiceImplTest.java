package org.dataprocess.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dataprocess.domain.Category;
import org.junit.Before;
import org.junit.Test;

public class DataCleanupServiceImplTest {

	private Category[] duplicateData;

	private DataProcessingService dataCleanupService;

	@Before
	public void setup() {
		dataCleanupService = new DataProcessingServiceImpl();
		duplicateData = createDataStub();
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

	@Test
	public void testCleanup() {
		Map<List<String>,TreeMap<String, Integer>> dedupedData = dataCleanupService
				.cleanup(duplicateData);
		assertNotNull(dedupedData);
	}
	
	@Test
	public void testSortCategoryCounterMap(){
		Map<String, Integer> sortedMapDescOnValue = dataCleanupService.getValidCategoriesWithCount(createDeDupedList());
		Object[] valuesArr = (Object[])sortedMapDescOnValue.values().toArray();
		assertEquals(String.valueOf(valuesArr[0]),"3");
		assertEquals(String.valueOf(valuesArr[1]),"2");
	}

	private List<Category> createDeDupedList(){
		List<Category> list = new ArrayList<Category>();
		list.add(new Category("Animal", "Dog"));
		list.add(new Category("Animal", "Cat"));
		list.add(new Category("Animal", "Snake"));
		list.add(new Category("Other", "Tree"));
		list.add(new Category("Other", "House"));
		return list;
	}
}
