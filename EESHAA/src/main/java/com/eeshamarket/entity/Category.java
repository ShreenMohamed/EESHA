package com.eeshamarket.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
 
    private static final long serialVersionUID = -1000119078147252957L;
 
    private String name;
    private byte[] image;

    
    
	@Id
    @Column(name = "Name", nullable = false , unique = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true )
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	} 
    
    

}
