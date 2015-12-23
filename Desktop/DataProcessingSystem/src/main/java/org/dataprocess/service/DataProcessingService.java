package org.dataprocess.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dataprocess.domain.Category;

public interface DataProcessingService {
	Map<List<String>,TreeMap<String, Integer>> cleanup(Category[] originalList);

	TreeMap<String, Integer> getValidCategoriesWithCount(
			List<Category> dedupedList);

	List<String> addCategory(String category);

	List<String> removeCategory(String category);
}
