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

@Named
@ViewScoped
public class UserEditGETBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_PERSON_LIST = "userList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User(); // Aktualnie edytowany użytkownik
    private User loaded = null; // Załadowany użytkownik

    @EJB
    UserDAO userDAO; // Obiekt DAO do obsługi operacji na użytkownikach

    @Inject
    FacesContext context; // Kontekst JSF dla zarządzania życia widoku

    @Inject
    Flash flash; // Umożliwia przechowywanie danych między żądaniami

    // Zwraca aktualnie edytowanego użytkownika
    public User getUser() {
        return user;
    }

    // Metoda wywoływana przy ładowaniu widoku
    public void onLoad() throws IOException {
        if (!context.isPostback()) { // Sprawdzenie, czy to nie jest postback (nowe żądanie)
            Integer userId = user.getUserId(); // Pobranie ID użytkownika
            if (userId != null && !context.isValidationFailed()) { // Jeśli ID użytkownika nie jest null i walidacja zakończyła się powodzeniem
                loaded = userDAO.find(userId); // Pobranie użytkownika z bazy danych
            }

            if (loaded != null) {
                user = loaded; // Ustawienie załadowanego użytkownika do edycji
            } else {
                addErrorMessage("Użytkownik nie został znaleziony w bazie danych."); // Komunikat o błędzie
            }
        }
    }

    // Metoda zapisująca zmiany w użytkowniku
    public String saveData() {
        if (user.getUserId() == null) {
            return PAGE_STAY_AT_THE_SAME; // Powrót do tej samej strony, jeśli brak ID użytkownika
        }

        try {
            if (user.getUserId() == null) {
                userDAO.create(user); // Tworzenie nowego rekordu użytkownika
            } else {
                userDAO.merge(user); // Aktualizacja istniejącego rekordu
            }
            return PAGE_PERSON_LIST; // Przekierowanie na listę użytkowników po zapisie
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Wystąpił błąd podczas zapisu."); // Komunikat o błędzie
            return PAGE_STAY_AT_THE_SAME; // Powrót do tej samej strony w przypadku błędu
        }
    }

    // Metoda dodająca komunikat o błędzie do kontekstu JSF
    private void addErrorMessage(String message) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
}