package org.dataprocess.service;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dataprocess.domain.Category;
import org.dataprocess.domain.CategoryComparator;
import org.dataprocess.domain.CategoryData;
import org.dataprocess.domain.CategoryNames;

public class DataProcessingServiceImpl implements DataProcessingService {

	/**
	 * Method that cleans up the data and returns the final sorted Map of elements
	 * @param Category Array - Original Input data
	 * @return Map containing the MAp of String and treeMap
	 */
	public Map<List<String>,TreeMap<String, Integer>> cleanup(Category[] originalData) {
		List<Category> categoryList = Arrays.asList(originalData);

		// LinkedHashSet used to preserve the order of the elements in the list
		List<Category> dedupedList = new ArrayList<Category>(
				new LinkedHashSet<Category>(categoryList));
		
		CategoryData categoryData = new CategoryData();
		categoryData.setListData(dedupedList);

		// Remove invalid categories
		dedupedList = removeInvalidCategoryFromList(dedupedList);
		List<String> dataList = new ArrayList<String>();
		for(Category cat : dedupedList){
			StringBuilder strBuild = new StringBuilder();
			strBuild.append(cat.getCategoryName()).append("|").append(cat.getSubCategoryName());
			dataList.add(strBuild.toString());
		}
		
		// Get the map of categories sorted in ascending order of their counts.
		TreeMap<String, Integer> sortedValidCategoryMap = getValidCategoriesWithCount(dedupedList);
		
		Map<List<String>,TreeMap<String, Integer>> returnValue = new HashMap<List<String>,TreeMap<String, Integer>>();
		returnValue.put(dataList, sortedValidCategoryMap);
		return returnValue;
	}

	/**
	 * Method that creates the Map of Category data with its corresponding count. It checks 
	 * to see if the key exists and increments the counter to reflect the counter of 
	 * similar category names.
	 * @param dedupedList
	 * @return Map<String,Integer>
	 */
	private Map<String, Integer> createCategoryCountMap(List<Category> dedupedList) {
		Map<String, Integer> categoryMap = new HashMap<String, Integer>();
		for(Category category : dedupedList){
			if(!categoryMap.containsKey(category.getCategoryName())) {
			categoryMap.put(category.getCategoryName(), 1);
			} else {
				categoryMap.put(category.getCategoryName(), categoryMap.get(category.getCategoryName())+1);
			}
		}
		return categoryMap;
	}

	/**
	 * This method removes the invalid category from the list of categories by iterating through the list
	 * @param dedupedList
	 * @return List<Category>
	 */
	private List<Category> removeInvalidCategoryFromList(List<Category> dedupedList) {
		for (Iterator<Category> categIter = dedupedList.iterator(); categIter
				.hasNext();) {
			Category category = categIter.next();
			if (category.getCategoryName().equalsIgnoreCase(CategoryNames.FOOD.toString())) {
				categIter.remove();
			}
		}
		return dedupedList;
	}

	/***
	 * This method returns Map of the valid categories and their corresponding count.
	 * @param List<Category> dedupedList
	 * @return TreeMap<String, Integer> 
	 */
	public TreeMap<String, Integer> getValidCategoriesWithCount(
			List<Category> dedupedList) {
		Map<String, Integer> unsortedMap = createCategoryCountMap(dedupedList);
		CategoryComparator categoryComparator = new CategoryComparator(unsortedMap);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(categoryComparator);
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}

	/**
	 * This method adds new category to the category list
	 * @param Catgory name
	 * @return List<String> Category list with names
	 */
	public List<String> addCategory(String categoryName) {
		List<String> categories = categoryList();
		if(categories.contains(categoryName.toUpperCase())) {
			categories.add(categoryName.toUpperCase());
		}
		return categories;
	}

	/**
	 * Method to remove category from the category list
	 * @param category name to be removed
	 * @return Updated category List 
	 */
	public List<String> removeCategory(String category) {
		List<String> categories = categoryList();
		categories.remove(category);
		return categories;
	}
	
	/**
	 * Static Category List
	 * @return List<String>
	 */
	private static List<String> categoryList() {
		List<String> categoryList = new ArrayList<String>();
		categoryList.add(CategoryNames.PERSON.toString());
		categoryList.add(CategoryNames.PLACE.toString());
		categoryList.add(CategoryNames.ANIMAL.toString());
		categoryList.add(CategoryNames.COMPUTER.toString());
		categoryList.add(CategoryNames.OTHER.toString());
		
		return categoryList;
	}
}
