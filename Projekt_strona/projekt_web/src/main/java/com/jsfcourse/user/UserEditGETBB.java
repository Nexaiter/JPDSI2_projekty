package com.jsfcourse.user;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import jsf.course.dao.UserDAO;
import jsf.course.entities.User;

@Named
@ViewScoped
public class UserEditGETBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_USER_REGISTER = "userRegisterPage?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User(); // Aktualnie edytowany użytkownik
    private User loaded = null; // Załadowany użytkownik

    
    @Inject
    FacesContext context; // Kontekst JSF dla zarządzania życia widoku
    
    @EJB
    UserDAO userDAO; // Obiekt DAO do obsługi operacji na użytkownikach


    public User getUser() {
        return user;
    }

    public void onLoad() throws IOException{
    	if(!context.isPostback()) {
    		if(!context.isValidationFailed() && user.getId() != null) {
    			loaded = userDAO.find(user.getId());
    		}
    		if (loaded != null) {
    			user = loaded;
    		} else {
    			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu getbb", null));
    		}
    	}
    }
    
    
    // Metoda zapisująca zmiany w użytkowniku
    public String saveData() {
       if (loaded == null) {
    	   return PAGE_STAY_AT_THE_SAME;
       }
       try {
    	   if (user.getId() == null) {
    		   userDAO.create(user);
    	   } else {
    		   userDAO.merge(user);
    	   }
       } catch (Exception e) {
    	   e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu getbb", null));
			return PAGE_STAY_AT_THE_SAME;
       }
       return PAGE_USER_REGISTER;
    }

    
}