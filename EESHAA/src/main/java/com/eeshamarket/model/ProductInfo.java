package com.eeshamarket.model;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.eeshamarket.entity.Product;
 
public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private String category;
    private boolean newProduct=false;
    private CommonsMultipartFile fileData;
 
    public ProductInfo() {
    }
 
    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
       this.category = product.getCategory().getName();
    }
 
    
 
    public ProductInfo(String code, String name, String category, double price) {
    	this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        }

	




	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
        return code;
    }
 

	public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
 
}
