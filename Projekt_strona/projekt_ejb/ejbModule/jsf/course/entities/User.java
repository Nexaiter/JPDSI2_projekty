package jsf.course.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String login;

	private String password;

	private String permission;

	//bi-directional many-to-one association to Gameorder
	@OneToMany(mappedBy="user")
	private List<Gameorder> gameorders;

	public User() {
	}

	public Integer getUserId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Gameorder> getGameorders() {
		return this.gameorders;
	}

	public void setGameorders(List<Gameorder> gameorders) {
		this.gameorders = gameorders;
	}

	public Gameorder addGameorder(Gameorder gameorder) {
		getGameorders().add(gameorder);
		gameorder.setUser(this);

		return gameorder;
	}

	public Gameorder removeGameorder(Gameorder gameorder) {
		getGameorders().remove(gameorder);
		gameorder.setUser(null);

		return gameorder;
	}

}