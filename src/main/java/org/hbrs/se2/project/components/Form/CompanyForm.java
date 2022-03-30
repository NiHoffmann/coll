package org.hbrs.se2.project.components.Form;

import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.se2.project.classes.User.Company;
//Class for form to allow a registered company to add information about itself
public class CompanyForm extends UserForm<Company> {
    final TextField nameField;
    //Constructor for creating a form for a specific company
    public CompanyForm(Company user) {
        super(user);
        getElement().getThemeList().add("dark");
        
        nameField = new TextField("Company name");
        //Checks if a name has been entered
        binder.forField(nameField)
            .withValidator(input -> !nameField.isVisible() || input.length() > 0, "Company name is required")
            .bind(Company::getName, Company::setName);

        passwordField.setVisible(false);

        layout.add(nameField);
        addForms();

        binder.readBean(user);
    }
    //Method for setting fields to read only
    public void setReadonly(boolean state) {
        nameField.setReadOnly(state);
        emailField.setReadOnly(state);
        passwordField.setReadOnly(state);
        streetField.setReadOnly(state);
        zipField.setReadOnly(state);
        cityField.setReadOnly(state);
        countryField.setReadOnly(state);
        phoneField.setReadOnly(state);
    }
}
