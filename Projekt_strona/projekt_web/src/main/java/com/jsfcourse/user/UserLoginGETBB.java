package com.jsfcourse.user;

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

import jsf.course.dao.UserDAO;
import jsf.course.entities.User;

@Named
@RequestScoped //Może tu byc potrzebny ten inny @ViewScoped
public class UserLoginGETBB {
	private static final String PAGE_USER_LOGIN = "userLoginPage?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private String login;
	private String password;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
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
	
	public String loginUser() {
		User user = new User();
		flash.put("user", user);
		return PAGE_USER_LOGIN;
	}	
	
}
