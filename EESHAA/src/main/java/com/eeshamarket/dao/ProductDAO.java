package com.eeshamarket.dao;

import java.util.List;

import com.eeshamarket.entity.Product;
import com.eeshamarket.model.ProductInfo;
 
public interface ProductDAO {
	
    public Product findProduct(String code);
    public ProductInfo findProductInfo(String code) ;
    public List<ProductInfo> queryProducts();
    public List<ProductInfo> listProducts(String name);
    public void save(ProductInfo productInfo);
    
}
