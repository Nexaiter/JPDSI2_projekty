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

	private static final String PAGE_USER_REGISTER = "userRegisterPage?faces-redirect=true";
    private static final String PAGE_USER_EDIT = "userEdit?faces-redirect=true"; // Strona do edycji użytkownika
    private static final String PAGE_STAY_AT_THE_SAME = null; // Strona pozostająca bez zmian


    private String name; // Nazwa do wyszukania gry

    @Inject
    ExternalContext extcontext; // Kontekst zewnętrzny

    @Inject
    Flash flash; // Obiekt Flash dla przekazywania danych między żądaniami

    @EJB
    ProductDAO productDAO; // Obiekt DAO do zarządzania użytkownikami

    // Gettery i settery
    
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
		
		//2. Get list
		list = productDAO.getList(searchParams);
		
		return list;
	}

    // Metoda tworząca nowego użytkownika i przekierowująca do edycji
    public String newProduct() {
        Product product = new Product();

        flash.put("product", product); // Przekazanie danych użytkownika za pomocą Flash

        return PAGE_STAY_AT_THE_SAME; // Przekierowanie do edycji nowego użytkownika
    }

    // Metoda edytująca istniejącego użytkownika i przekierowująca do edycji
    public String editProduct(Product product) {
        flash.put("product", product); // Przekazanie danych użytkownika za pomocą Flash

        return PAGE_STAY_AT_THE_SAME; // Przekierowanie do edycji użytkownika
    }

    // Metoda usuwająca użytkownika
    public String deleteProduct(Product product) {
        productDAO.remove(product); // Usunięcie użytkownika z bazy danych
        return PAGE_STAY_AT_THE_SAME; // Powrót do tej samej strony
    }
    
    
}