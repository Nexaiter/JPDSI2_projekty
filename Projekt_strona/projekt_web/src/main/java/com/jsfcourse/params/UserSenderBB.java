package com.jsfcourse.params;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jsf.course.dao.UserDAO;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class UserSenderBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String permission;

	@Inject
	FacesContext ctx;

	@EJB
	UserDAO userDAO;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	private void getPermissionFromDB() {
		this.permission = userDAO.getPermission(this.login);
	}

	public String getLoggedUser() {
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
		if (session != null) {
			return (String) session.getAttribute("login");
		}
		return null; 
	}

	public String sendThroughSession() {
		getPermissionFromDB();
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		session.setAttribute("login", login);
		session.setAttribute("password", password);
		session.setAttribute("permission", permission);

		return "shopMainPage?faces-redirect=true";
	}
	
	public String logout() {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    HttpSession session = (HttpSession) ec.getSession(false);
	    
	    if (session != null) {
	        session.invalidate(); // Usunięcie całej sesji
	    }
	    
	    return "shopMainPage?faces-redirect=true"; // Przekierowanie na stronę logowania
	}

}
