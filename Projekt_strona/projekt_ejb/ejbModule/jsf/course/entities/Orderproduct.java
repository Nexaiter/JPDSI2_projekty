package jsf.course.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;


/**
 * The persistent class for the orderproduct database table.
 * 
 */
@Entity
@NamedQuery(name = "Orderproduct.findAll", query = "SELECT o FROM Orderproduct o")
public class Orderproduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Dodanie klucza głównego
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Definicja strategii generowania wartości klucza
    private Long id; // Nowe pole identyfikatora

    private double price;

    //bi-directional many-to-one association to Gameorder
    @ManyToOne
    @JoinColumn(name="gameOrderID")
    private Gameorder gameorder;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name="productID")
    private Product product;

    public Orderproduct() {
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Gameorder getGameorder() {
        return this.gameorder;
    }

    public void setGameorder(Gameorder gameorder) {
        this.gameorder = gameorder;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Gettery i settery dla id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}