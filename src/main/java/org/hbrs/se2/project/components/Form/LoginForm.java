package org.hbrs.se2.project.components.Form;

import java.util.function.Consumer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import org.hbrs.se2.project.control.UserControl;

/**
 * Custom loginform which is a bit smaller
 */
public class LoginForm extends Div {

    final FormLayout layout;
    final Binder<TemporaryUser> binder;
    final TemporaryUser user;
    final Button loginButton;

    /**
     * Connectable to a lambda which runs when the login process was successful
     */
    public Runnable onSuccess;
    /**
     * Connectable to a lambda which runs when the login process was unsuccessful
     * @param Exception The exception why the login process failed
     */
    public Consumer<Exception> onError;
    
    public LoginForm() {
        layout = new FormLayout();
        binder = new Binder<>();
        user = new TemporaryUser();

        getElement().getThemeList().add("dark");
        getStyle().set("padding-left", "5px").set("padding-right", "5px");

        EmailField usernameField = new EmailField("Email");
        usernameField.setRequiredIndicatorVisible(true);
        usernameField.setId("EmailField");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setId("PasswordField");
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setPlaceholder("5-30 characters");
        //Checks if entered E-Mail is a valid E-Mail
        binder.forField(usernameField)
            .withValidator(input -> input.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.([a-zA-Z0-9-.]{2,4})$"), "Email is required and must match format")
            .bind(TemporaryUser::getEmail, TemporaryUser::setEmail);
        //Checks if a password has been entered
        binder.forField(passwordField)
            .withValidator(input -> input.length() > 0, "Password is required")
            .bind(TemporaryUser::getPassword, TemporaryUser::setPassword);

        loginButton = new Button("Log in", event -> {
            try {
                binder.writeBean(user);
                //Executes login process for the user
                UserControl.login(user.getEmail(), user.getPassword());

                if (onSuccess != null) {
                   onSuccess.run();
                };
            }
            catch (ValidationException e) {
                if (onError != null) {onError.accept(e);};
            }
            catch (Exception e) {
                Notification note = Notification.show(e.getMessage());
                note.setPosition(Position.TOP_CENTER);
                note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                note.setDuration(5000);

                if (onError != null) {onError.accept(e);};

                e.printStackTrace();
            }
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.setColspan(usernameField, 2);
        layout.setColspan(passwordField, 2);

        layout.add(usernameField, passwordField);
        add(layout, loginButton);
    }

    /**
     * Gets the user associated with this form
     * @return
     */
    public TemporaryUser getUser() {
        return this.user;
    }

    public FormLayout getFormLayout() {
        return this.layout;
    }

    public Button getLoginButton() {
        return this.loginButton;
    }
}

class TemporaryUser {
    private String email;
    private String password;

    protected TemporaryUser() {

    }

    protected TemporaryUser(String email, String password) {
        this();

        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email.trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password.trim();
    }
    
}
