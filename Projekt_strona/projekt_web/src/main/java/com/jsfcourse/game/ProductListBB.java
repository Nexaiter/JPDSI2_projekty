package com.jsfcourse.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpSession;
import jsf.course.dao.ProductDAO;
import jsf.course.entities.Product;

@Named
@RequestScoped
public class ProductListBB {

    private static final String PAGE_STAY_AT_THE_SAME = null; 
    private static final String PAGE_LIST_PAGE = "shopProductListView?faces-redirect=true"; 
    private static final String PAGE_EDIT_PRODUCT = "shopEditGamePage?faces-redirect=true"; 


    private String name;

    @Inject
    ExternalContext extcontext;
    @Inject
    Flash flash; 
    @EJB
    ProductDAO productDAO;
   
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

    public List<Product> getFullList() {
        return productDAO.getFullList();
    }


    public List<Product> getList(){
		List<Product> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
			
		} 
			list = productDAO.getList(searchParams);
		return list;
	}
   
    public String newProduct() {
        Product product = new Product();

        flash.put("product", product);

        return PAGE_EDIT_PRODUCT; 
    }


    public String editProduct(Product product) {
        flash.put("product", product); 

        return PAGE_EDIT_PRODUCT; 
    }


    public String removeProduct(Product product) {
        productDAO.remove(product); 
        return PAGE_LIST_PAGE;
    }
    
    
}