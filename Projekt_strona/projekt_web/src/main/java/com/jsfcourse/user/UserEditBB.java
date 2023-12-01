package com.jsfcourse.user;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import jsf.course.dao.UserDAO;
import jsf.course.entities.User;

@Named // Bean zarządzany przez CDI
@ViewScoped // Życie bean'a związane z cyklem widoku JSF
public class UserEditBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_REGISTER_PAGE = "userRegisterPage?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User(); // Obiekt użytkownika
    private User loaded = null; // Załadowany użytkownik

    @EJB // Wstrzyknięcie zależności - interfejs DAO
    UserDAO userDAO;

    @Inject // Wstrzyknięcie zależności - kontekst JSF
    FacesContext context;

    @Inject // Wstrzyknięcie zależności - obiekt FlashScoped
    Flash flash;

    // Metoda zwracająca aktualnie edytowanego użytkownika
    public User getUser() {
        return user;
    }

    // Metoda wywoływana przy ładowaniu widoku
    public void onLoad() throws IOException {
        loaded = (User) flash.get("user");

        // Ustawienie załadowanego użytkownika, jeśli istnieje
        if (loaded != null) {
            user = loaded;
        } else {
        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu bb", null));
        }
    }

    public String saveData() {
		 //no Person object passed
		if (loaded == null) {
			loaded = user;
		}

		try {
			if (user.getId() == null) {
				// new record
				userDAO.create(user);
			} else {
				// existing record
				userDAO.merge(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu bb", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_REGISTER_PAGE;
	}

    
}
