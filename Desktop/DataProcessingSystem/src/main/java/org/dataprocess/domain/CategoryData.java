package org.dataprocess.domain;

import java.io.Serializable;
import java.util.List;

public class CategoryData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Category> listData;

	/**
	 * @return the listData
	 */
	public List<Category> getListData() {
		return listData;
	}

	/**
	 * @param listData the listData to set
	 */
	public void setListData(List<Category> listData) {
		this.listData = listData;
	}
}
