package com.eeshamarket.daoImp;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eeshamarket.dao.CategoryDAO;
import com.eeshamarket.dao.ProductDAO;
import com.eeshamarket.entity.Category;
import com.eeshamarket.entity.Product;
import com.eeshamarket.model.ProductInfo;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CategoryDAO categoryDAO;
   
   public Product findProduct(String code) {
    	Product s = (Product) sessionFactory.getCurrentSession().get(Product.class, code);
    			return  s;
    }
   
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(),product.getCategory().getName() ,product.getPrice());
        
    }
    
    public void save(ProductInfo productInfo) {
        String code = productInfo.getCode();
        Product product = null;
        Category category =null ;
       
       category= categoryDAO.findCategoryName(productInfo.getCategory());
       sessionFactory.getCurrentSession().save(category);

        boolean isNew = false;

        if (code != null) {
            product = this.findProduct(code);
            
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setCategory(category);
 
        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().save(product);
        }
        this.sessionFactory.getCurrentSession().flush();
    }
    
    public List<ProductInfo> queryProducts() {
    	List list = sessionFactory.getCurrentSession().createQuery("from Product")
				.list();
		return list; 
    }

	@Override
	public List<ProductInfo> listProducts(String name) {
		
		String sql = "Select new " + ProductInfo.class.getName() 
	                + "( p.code, p.name ,p.category.name ,p.price ) "
	                + " from " + Product.class.getName() + " p "
	                + " where p.category.name = :name ";
	        
	        Session session = this.sessionFactory.getCurrentSession();
	        
	        Query query = session.createQuery(sql);
	        query.setParameter("name", name);
	 
	        return query.list();
	    }
	}
   


	
    
