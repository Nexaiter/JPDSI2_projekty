package jsf.course.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String description;

	@Lob
	private String image;

	private String name;

	private BigDecimal price;

	//bi-directional many-to-one association to Gameorder
	@OneToMany(mappedBy="product")
	private List<Gameorder> gameorders;

	//bi-directional many-to-one association to Orderproduct
	@OneToMany(mappedBy="product")
	private List<Orderproduct> orderproducts;

	public Product() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Gameorder> getGameorders() {
		return this.gameorders;
	}

	public void setGameorders(List<Gameorder> gameorders) {
		this.gameorders = gameorders;
	}

	public Gameorder addGameorder(Gameorder gameorder) {
		getGameorders().add(gameorder);
		gameorder.setProduct(this);

		return gameorder;
	}

	public Gameorder removeGameorder(Gameorder gameorder) {
		getGameorders().remove(gameorder);
		gameorder.setProduct(null);

		return gameorder;
	}

	public List<Orderproduct> getOrderproducts() {
		return this.orderproducts;
	}

	public void setOrderproducts(List<Orderproduct> orderproducts) {
		this.orderproducts = orderproducts;
	}

	public Orderproduct addOrderproduct(Orderproduct orderproduct) {
		getOrderproducts().add(orderproduct);
		orderproduct.setProduct(this);

		return orderproduct;
	}

	public Orderproduct removeOrderproduct(Orderproduct orderproduct) {
		getOrderproducts().remove(orderproduct);
		orderproduct.setProduct(null);

		return orderproduct;
	}

}