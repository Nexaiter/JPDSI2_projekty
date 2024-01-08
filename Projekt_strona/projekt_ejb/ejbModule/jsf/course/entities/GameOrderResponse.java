package jsf.course.entities;



import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jsf.course.dao.GameOrderDAO;
import jsf.course.dao.ProductDAO;
import jsf.course.dao.UserDAO;


public class GameOrderResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer totalPrice;
	private Integer amount;
	private String name;
	
	
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
	FacesContext ctx;
	
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
