package com.jsfcourse.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jsfcourse.params.UserSenderBB;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jsf.course.dao.GameOrderDAO;
import jsf.course.dao.ProductDAO;
import jsf.course.dao.UserDAO;
import jsf.course.entities.GameOrderResponse;
import jsf.course.entities.Gameorder;
import jsf.course.entities.Product;

@Named
@RequestScoped
public class GameOrderListBB {

	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Integer id;

	@Inject
	ExternalContext extcontext;
	@Inject
	Flash flash;
	@EJB
	ProductDAO productDAO;
	@EJB
	UserDAO userDAO;
	@EJB
	GameOrderDAO gameOrderDAO;
	@Inject
	UserSenderBB userSender;

	@Inject
	FacesContext ctx;

	private Integer productID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Gameorder> getFullList() {
		return gameOrderDAO.getFullList();
	}

	public List<GameOrderResponse> Koszyk() {
		return getList();
	}

	public List<GameOrderResponse> getList() {
		List<Gameorder> list = null;
		List<GameOrderResponse> list2 = new ArrayList<GameOrderResponse>();

		Integer userID = userSender.getUserId();

		list = gameOrderDAO.getList(userDAO.find(userID));

		for (Gameorder x : list) {
		    var zmienna = new GameOrderResponse();
		    zmienna.setAmount(x.getAmount());
		    zmienna.setName(x.getProduct().getName());
		    zmienna.setTotalPrice(x.getPrice());

		    boolean containsName = false;
		    for (GameOrderResponse existingOrder : list2) {
		        if (existingOrder.getName().equals(zmienna.getName())) {
		            containsName = true;
		            // Zwiększenie ceny o cenę nowego produktu
		            existingOrder.setTotalPrice(existingOrder.getTotalPrice() + zmienna.getTotalPrice());
		            // Zwiększenie amount o 1
		            existingOrder.setAmount(existingOrder.getAmount() + 1);
		            break;
		        }
		    }

		    // Jeśli nie zawiera, dodaj do listy
		    if (!containsName) {
		        list2.add(zmienna);
		    }
		}

		return list2;
	}

	public String newGameOrder() {
		Gameorder gameOrder = new Gameorder();

		flash.put("gameOrder", gameOrder);

		return PAGE_STAY_AT_THE_SAME;
	}

	/*
	 * public String newOrder(Product product) { Gameorder gameOrder = new
	 * Gameorder();
	 * 
	 * HttpSession session = (HttpSession)
	 * ctx.getExternalContext().getSession(false); var userId = (Integer)
	 * session.getAttribute("id");
	 * 
	 * gameOrder.setUser(userDAO.find(userId)); gameOrder.setProduct(product);
	 * 
	 * gameOrderDAO.create(gameOrder);
	 * 
	 * return PAGE_STAY_AT_THE_SAME; }
	 */
	
	public String newOrder(Product product) {
	    HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
	    Integer userId = (Integer) session.getAttribute("id");

	    // Pobierz listę zamówień użytkownika
	    List<Gameorder> userOrders = gameOrderDAO.getList(userDAO.find(userId));

	    // Sprawdź, czy produkt już istnieje w zamówieniach użytkownika
	    boolean productExists = userOrders.stream().anyMatch(order -> order.getProduct().getId().equals(product.getId()));

	    // Jeśli produkt już istnieje, zwiększ ilość i cenę
	    if (productExists) {
	        for (Gameorder order : userOrders) {
	            if (order.getProduct().getId().equals(product.getId())) {
	                order.setAmount(order.getAmount() + 1);
	                order.setPrice(order.getPrice() + product.getPrice());
	                gameOrderDAO.merge(order);
	                break;
	            }
	        }
	    } else {
	        // Jeśli nie istnieje, stwórz nowe zamówienie z ilością 1 i ceną produktu
	        Gameorder newOrder = new Gameorder();
	        newOrder.setUser(userDAO.find(userId));
	        newOrder.setProduct(product);
	        newOrder.setAmount(1);
	        newOrder.setPrice(product.getPrice());
	        gameOrderDAO.create(newOrder);
	    }

	    return PAGE_STAY_AT_THE_SAME;
	}

	public String editProduct(Gameorder gameOrder) {
		flash.put("gameOrder", gameOrder);

		return PAGE_STAY_AT_THE_SAME;
	}

	public String removeProduct(Gameorder gameOrder) {
		gameOrderDAO.remove(gameOrder);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String removeOrders() {
		Integer userID = userSender.getUserId();
		gameOrderDAO.deleteUserOrdersByUserId(userID);		
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
