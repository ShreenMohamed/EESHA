package com.eeshamarket.dao;

import java.util.List;

import com.eeshamarket.entity.Category;
import com.eeshamarket.model.CategoryInfo;


public interface CategoryDAO {

   public List<CategoryInfo> getAllCategory();
   public Category findCategoryName(String name);
   public CategoryInfo findCategoryInfo(String name);
   public void save(CategoryInfo categoryInfo);

}
