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

    private static final String PAGE_USER_LIST = "userList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User(); // Obiekt użytkownika
    private User loaded = null; // Załadowany użytkownik

    @EJB // Wstrzyknięcie zależności - interfejs DAO
    private UserDAO userDAO;

    @Inject // Wstrzyknięcie zależności - kontekst JSF
    private FacesContext context;

    @Inject // Wstrzyknięcie zależności - obiekt FlashScoped
    private Flash flash;

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
            addErrorMessage("Błędne użycie systemu");
        }
    }

    // Metoda zapisująca dane użytkownika
    public String saveData() {
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }

        try {
            // Zapisanie nowego rekordu lub aktualizacja istniejącego
            if (user.getUserId() == null) {
                userDAO.create(user);
            } else {
                userDAO.merge(user);
            }
            return PAGE_USER_LIST; // Przekierowanie po zapisie
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Wystąpił błąd podczas zapisu");
            return PAGE_STAY_AT_THE_SAME;
        }
    }

    // Metoda dodająca komunikat o błędzie
    private void addErrorMessage(String message) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
}
