package org.dataprocess.domain;

public class Category {
	private String categoryName;
	private String subCategoryName;

	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param subCategoryName
	 *            the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Category(String categoryName, String subCategoryName) {
		this.categoryName = categoryName;
		this.subCategoryName = subCategoryName;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public boolean equals(Object anotherObj) {
		if (!(anotherObj instanceof Category)) {
			return false;
		}
		Category tempCategory = (Category) anotherObj;
		if (tempCategory.getCategoryName().equals(this.categoryName)
				&& tempCategory.getSubCategoryName().equals(
						this.getSubCategoryName())) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (this.categoryName + this.categoryName).hashCode();
	}
}
