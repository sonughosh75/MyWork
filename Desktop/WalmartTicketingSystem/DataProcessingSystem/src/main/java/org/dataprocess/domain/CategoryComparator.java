package org.dataprocess.domain;

import java.util.Comparator;
import java.util.Map;

public class CategoryComparator implements Comparator<String> {
	Map<String,Integer> categoryMap;
	
	public CategoryComparator(Map<String,Integer> catMap) {
		this.categoryMap = catMap;
	}
	public int compare(String val1, String val2) {
		if(this.categoryMap.get(val2) > this.categoryMap.get(val1)){
			return 1;
		} else {
			return -1;
		}
	}

}
