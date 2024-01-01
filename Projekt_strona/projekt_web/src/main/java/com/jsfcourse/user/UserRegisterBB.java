package com.jsfcourse.user;

import java.io.IOException;
import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import jsf.course.dao.UserDAO;
import jsf.course.entities.User;

@Named
@RequestScoped
public class UserRegisterBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_USER_REGISTER = "userRegisterPage?faces-redirect=true";
    private static final String PAGE_USER_LOGIN = "userLoginPage?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User();
    private User loaded = null;
    private String login;
    private String password;

    @Inject
    ExternalContext extcontext;

    @Inject
    FacesContext context;

    @Inject
    Flash flash;

    @Inject
    UserDAO userDAO;

    public User getUser() {
        return user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String registerUser() {
        User user = new User();
        flash.put("user", user);
        return PAGE_USER_REGISTER;
    }

    public void onLoad() throws IOException {
        loaded = (User) flash.get("user");

        if (loaded != null) {
            user = loaded;
        } else {
        }
    }

    public String saveData() {
        // no Person object passed
        if (loaded == null) {
            loaded = user;
        }

        try {
            // Check if the user with the provided login already exists
            User existingUser = userDAO.findUserByLogin(user.getLogin());

            if (existingUser != null) {
                // User with the provided login already exists, add a faces message
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik o podanym loginie już istnieje!", null);
                context.addMessage(null, message);
                return PAGE_STAY_AT_THE_SAME; // Stay on the same page
            }

            // If the user doesn't exist, proceed with saving
            if (user.getId() == null) {
                userDAO.create(user);
            } else {
                userDAO.merge(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu bb", null));
            return PAGE_STAY_AT_THE_SAME;
        }
        return PAGE_USER_LOGIN;
    }

    @FacesValidator("permissionValidator")
    public static class PermissionValidator implements Validator<String> {

        @Override
        public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
            if (value != null && !(value.equals("user") || value.equals("admin"))) {
                FacesMessage message = new FacesMessage("Dozwolone wartości to 'user' lub 'admin'");
                throw new ValidatorException(message);
            }
        }
    }
}
