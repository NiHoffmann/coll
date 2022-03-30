package org.hbrs.se2.project.components.Form;

import org.hbrs.se2.project.classes.User.Student;
//Class for form needed fo registering a student
public class StudentRegister extends StudentForm {
    public StudentRegister() {
        super(new Student());
        user.setCountry("Germany");

        // remove(certsField, qualificationField, cvField);

        certsField.setVisible(false);
        qualificationField.setVisible(false);
        cvField.setVisible(false);
        passwordField.setVisible(true);

        binder.setBean(user);
    }
}
