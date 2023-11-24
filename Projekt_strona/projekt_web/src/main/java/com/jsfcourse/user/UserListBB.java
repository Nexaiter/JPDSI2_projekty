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
@RequestScoped
public class UserListBB {

    private static final String PAGE_USER_EDIT = "userEdit?faces-redirect=true"; // Strona do edycji użytkownika
    private static final String PAGE_STAY_AT_THE_SAME = null; // Strona pozostająca bez zmian

    private String login; // Login do wyszukania użytkownika

    @Inject
    ExternalContext extcontext; // Kontekst zewnętrzny

    @Inject
    Flash flash; // Obiekt Flash dla przekazywania danych między żądaniami

    @EJB
    UserDAO userDAO; // Obiekt DAO do zarządzania użytkownikami

    // Gettery i settery

    // Metoda zwracająca pełną listę użytkowników
    public List<User> getFullList() {
        return userDAO.getFullList();
    }

    // Metoda pobierająca listę użytkowników na podstawie loginu
    public List<User> getList() {
        List<User> list = null;

        Map<String, Object> searchParams = new HashMap<>();

        // Sprawdzenie, czy login nie jest pusty ani nie składa się tylko z białych znaków
        if (login != null && !login.trim().isEmpty()) {
            searchParams.put("login", login); // Dodanie loginu jako kryterium wyszukiwania
        }

        list = userDAO.getUserList(searchParams); // Pobranie listy użytkowników na podstawie kryteriów wyszukiwania

        return list;
    }

    // Metoda tworząca nowego użytkownika i przekierowująca do edycji
    public String newUser() {
        User user = new User();

        flash.put("user", user); // Przekazanie danych użytkownika za pomocą Flash

        return PAGE_USER_EDIT; // Przekierowanie do edycji nowego użytkownika
    }

    // Metoda edytująca istniejącego użytkownika i przekierowująca do edycji
    public String editPerson(User user) {
        flash.put("user", user); // Przekazanie danych użytkownika za pomocą Flash

        return PAGE_USER_EDIT; // Przekierowanie do edycji użytkownika
    }

    // Metoda usuwająca użytkownika
    public String deletePerson(User user) {
        if (user != null) {
            userDAO.remove(user); // Usunięcie użytkownika z bazy danych
        }

        return PAGE_STAY_AT_THE_SAME; // Powrót do tej samej strony
    }
}