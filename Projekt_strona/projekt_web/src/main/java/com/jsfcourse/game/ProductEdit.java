package com.jsfcourse.game;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import jsf.course.dao.ProductDAO;
import jsf.course.entities.Product;

@Named
@ViewScoped
public class ProductEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PRODUCT_LIST = "shopProductListView?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Product product = new Product();
	private Product loaded = null;

	@Inject
	FacesContext context;

	@EJB
	ProductDAO productDAO;

	@Inject
	Flash flash;

	public Product getProduct() {
		return product;
	}

	public void onLoad() throws IOException {
		loaded = (Product) flash.get("product");
		if (loaded != null) {
			product = loaded;
		} else {
		
		}
	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (product.getId() == null) {
				productDAO.create(product);
				return PAGE_PRODUCT_LIST;
			} else {
				// existing record
				productDAO.merge(product);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Added", null));

				return PAGE_PRODUCT_LIST;

			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
		}
		return PAGE_STAY_AT_THE_SAME;
	}
}
