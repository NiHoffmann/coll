package org.hbrs.se2.project.components.Form;

import java.util.Arrays;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import org.hbrs.se2.project.classes.User.User;
import org.hbrs.se2.project.util.Globals;

/**
 * Generic form component for a User.
 * Fields that are not visible will not be validated
 */
public class UserForm<T extends User> extends Div implements UserFormIF {
    protected final FormLayout layout;
    public final Binder<T> binder;

    T user;

    final EmailField emailField;
    final PasswordField passwordField;
    final TextField streetField;
    final TextField zipField;
    final TextField cityField;
    final ComboBox<String> countryField;
    final TextField phoneField;

    private UserForm() {
        this.getElement().getThemeList().add("dark");
        layout = new FormLayout();
        binder = new Binder<T>();

        emailField = new EmailField("Email");
        passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("5-30 characters");
        streetField = new TextField("Street");
        zipField = new TextField("Zip Code");
        cityField = new TextField("City");
        countryField = new ComboBox<String>("Country", Arrays.asList(Globals.COUNTRIES));
        phoneField = new TextField("Phone number (optional)");

        add(layout);
    }

    protected UserForm(T user) {
        this();

        this.user = user;

        binder.forField(emailField)
            .withValidator(input -> !emailField.isVisible() || input.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.([a-zA-Z0-9-.]{2,4})$"), "Email is required and must match format")
            .bind(T::getEmail, T::setEmail);

        binder.forField(passwordField)
            .withValidator(input -> !passwordField.isVisible() || input.length() >= 5 && input.length() <= 30, "Password must be between 5 and 30 characters")
            .bind(T::getPassword, T::setPassword);

        binder.forField(streetField)
            .withValidator(input -> !streetField.isVisible() || input.length() > 0, "Street is required")
            .bind(T::getStreet, T::setStreet);
        
        binder.forField(zipField)
            .withValidator(input -> !zipField.isVisible() || input.length() > 0, "Zip is required")
            .bind(T::getZip, T::setZip);

        binder.forField(cityField)
            .withValidator(input -> !cityField.isVisible() || input.length() > 0, "City is required")
            .bind(T::getCity, T::setCity);

        binder.forField(countryField).bind(T::getCountry, T::setCountry);
        binder.forField(phoneField).bind(T::getPhone, T::setPhone);

        binder.readBean(user);
    }

    public T getUser() {
        return user;
    }

    protected void addForms() {
        layout.add(
            emailField, passwordField,
            streetField, zipField,
            cityField, countryField,
            phoneField
        );
    }

    protected void onInvalidWrite(ValidationException e) {
        new Notification(e.getMessage());
        e.printStackTrace();
    }

    /**
     * Set fields to be readonly incase not editing
     * @param state
     */
    @Override
    public void setReadonly(boolean state) {
        emailField.setReadOnly(state);
        passwordField.setReadOnly(state);
        streetField.setReadOnly(state);
        zipField.setReadOnly(state);
        cityField.setReadOnly(state);
        countryField.setReadOnly(state);
        phoneField.setReadOnly(state);
    }

    /**
     * If the form is visible on someone elses profile this will hide personal fields
     * @param state
     */
    @Override
    public void setPublic(boolean state) {
        passwordField.setVisible(state);
        streetField.setVisible(state);
        zipField.setVisible(state);
        phoneField.setVisible(state);
    }
}
