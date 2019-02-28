package com.eeshamarket.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.eeshamarket.entity.Category;

public class CategoryInfo {
	   
		private String name;	 
	    private CommonsMultipartFile fileData;
	    private boolean newCategory=false;

	    
	    
		public CategoryInfo(String name) {
			
			this.name = name;
		}

		public CategoryInfo(Category category) {
			
			//this.id = category.getId();
			this.name=category.getName();
		}

		public boolean isNewCategory() {
			return newCategory;
		}

		public void setNewCategory(boolean newCategory) {
			this.newCategory = newCategory;
		}

		public CategoryInfo() {

		}
		
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public CommonsMultipartFile getFileData() {
			return fileData;
		}
		public void setFileData(CommonsMultipartFile fileData) {
			this.fileData = fileData;
		}
	    
	    
	
	
	
}
