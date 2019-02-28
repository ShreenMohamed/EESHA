package com.eeshamarket.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.eeshamarket.dao.CategoryDAO;
import com.eeshamarket.entity.Category;
import com.eeshamarket.model.CategoryInfo;

@Component
public class CategoryInfoValidator implements Validator {
	
    @Autowired
    private CategoryDAO categoryDAO;
	 
	    public boolean supports(Class<?> clazz) {
	        return clazz == CategoryInfo.class;
	    }
	 
	    public void validate(Object target, Errors errors) {
	        CategoryInfo categoryInfo = (CategoryInfo) target;
	 
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.categoryForm.name");

	 
	        String name = categoryInfo.getName();
	        if (name != null && name.length() > 0) {
	            if (name.matches("\\s+")) {
	                errors.rejectValue("name", "Pattern.categoryForm.code");
	            } else if(categoryInfo.isNewCategory()) {
	                Category category = categoryDAO.findCategoryName(name);
	                if (category != null) {
	                    errors.rejectValue("name", "Duplicate.categoryForm.name");
	                }
	            }
	        }
	    }
	 
	}
