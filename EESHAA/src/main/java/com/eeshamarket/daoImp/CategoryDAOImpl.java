package com.eeshamarket.daoImp;

import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.eeshamarket.dao.CategoryDAO;
import com.eeshamarket.entity.Category;
import com.eeshamarket.model.CategoryInfo;

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	 private SessionFactory sessionFactory;

	@Override
	public List<CategoryInfo> getAllCategory() {
		List list = sessionFactory.getCurrentSession().createQuery("from Category")
				.list();
		return list; 
	}

	@Override
	public Category findCategoryName(String name) {
		Category s = (Category) sessionFactory.getCurrentSession().get(Category.class, name);
		return  s;
	    }
	
	@Override
	public CategoryInfo findCategoryInfo(String name) {
	        Category category = this.findCategoryName(name);
	        if (category == null) {
	            return null;
	            }
	        return new CategoryInfo(category.getName());
	    }
	
	@Override
	public void save(CategoryInfo categoryInfo) {
		    String name = categoryInfo.getName();
	        Category category = null;
	        boolean isNew = false;
	        if (name!= null) {
	        	category = this.findCategoryName(name);
	        }
	        if (category == null) {
	            isNew = true;
	            category = new Category();
	        }
	        category.setName(categoryInfo.getName());
	 
	        if (categoryInfo.getFileData() != null) {
	            byte[] image = categoryInfo.getFileData().getBytes();
	            if (image != null && image.length > 0) {
	            	category.setImage(image);
	            }
	        }
	        if (isNew) {
	            this.sessionFactory.getCurrentSession().save(category);
	        }
	        this.sessionFactory.getCurrentSession().flush();
		
		
	}
}
