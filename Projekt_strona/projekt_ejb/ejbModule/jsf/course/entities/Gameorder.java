package jsf.course.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the gameorder database table.
 * 
 */
@Entity
@NamedQuery(name="Gameorder.findAll", query="SELECT g FROM Gameorder g")
public class Gameorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private Integer amount;

	private Integer price;	

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productID")
	private Product product;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userID")
	private User user;

	//bi-directional many-to-one association to Orderproduct
	@OneToMany(mappedBy="gameorder")
	private List<Orderproduct> orderproducts;

	public Gameorder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return this.user;
	}
	
	public Integer getUserID() {
		return this.user.getId();
	}
	
	public void setUserID(Integer id) {
		this.user.setId(id);
	}
	
	public Integer getProductID() {
		return this.product.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Orderproduct> getOrderproducts() {
		return this.orderproducts;
	}

	public void setOrderproducts(List<Orderproduct> orderproducts) {
		this.orderproducts = orderproducts;
	}

	public Orderproduct addOrderproduct(Orderproduct orderproduct) {
		getOrderproducts().add(orderproduct);
		orderproduct.setGameorder(this);

		return orderproduct;
	}

	public Orderproduct removeOrderproduct(Orderproduct orderproduct) {
		getOrderproducts().remove(orderproduct);
		orderproduct.setGameorder(null);

		return orderproduct;
	}

}