package org.hbrs.se2.project.components.Form;

import org.hbrs.se2.project.classes.User.Company;
//Class for form needed fo registering a company
public class CompanyRegister extends CompanyForm {
    public CompanyRegister() {
        super(new Company());
        user.setCountry("Germany");

        layout.setColspan(nameField, 2);

        passwordField.setVisible(true);
        //passwordField.setRequired(true);

        // nameField.setRequired(true);
        // streetField.setRequired(true);
        // zipField.setRequired(true);
        // cityField.setRequired(true);
        // countryField.setRequired(true);
        // phoneField.setRequired(true);

        //binder.getFields().forEach(binder::removeBinding);

        binder.setBean(user);
    }
}
