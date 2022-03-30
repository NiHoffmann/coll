package org.hbrs.se2.project.components.Form;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;

import org.hbrs.se2.project.classes.User.Student;
//Class for form to allow a registered student to add information about themselves
public class StudentForm extends UserForm<Student> {

    public final ComboBox<String> genderField;
    public final TextField firstnameField;
    public final TextField lastnameField;
    public final DatePicker birthdateField;
    public final ComboBox<String> qualificationField;
    public final Upload cvField;
    public final Upload certsField;
    //Constructor for creating a form for a specific student
    public StudentForm(Student user) {
        super(user);

        getElement().getThemeList().add("dark");

        genderField = new ComboBox<String>("Gender", Arrays.asList("Male", "Female"));
        firstnameField = new TextField("First name");
        lastnameField = new TextField("Last name");
        birthdateField = new DatePicker("Date of birth");

        //qualifications
        qualificationField = new ComboBox<String>("Qualifications", Arrays.asList(
            "Realschule", "Fachabitur", "Gymnasium", "Other (please provide in certificates)"
        ));

        //cv
        //Label cvLabel = new Label("Curriculum Vitae (.pdf)");
        cvField = new Upload();
        cvField.setAcceptedFileTypes(".pdf");

        //certificates
        //Label certsLabel = new Label("Certificates (.pdf)");
        certsField = new Upload();
        certsField.setAcceptedFileTypes(".pdf");
        certsField.setMaxFiles(10);
        //Checks if a gender has been selected
        binder.forField(genderField)
            .withValidator(input -> !genderField.isVisible() || input.length() > 0, "Gender must be selected")
            .bind(Student::getGender, Student::setGender);
        //Checks if a first name has been entered
        binder.forField(firstnameField)
            .withValidator(input -> !firstnameField.isVisible() || input.length() > 0, "First name is required")
            .bind(Student::getFirstname, Student::setFirstname);
        //Ches if a last name has been entered
        binder.forField(lastnameField)
            .withValidator(input -> !lastnameField.isVisible() || input.length() > 0, "Last name is required")
            .bind(Student::getLastname, Student::setLastname);
        //Checks if a student that is trying to register is at least 18 years old
        binder.forField(birthdateField)
            .withValidator(input -> {
                if (!birthdateField.isVisible())
                    return true;
                int age = Period.between(input, LocalDate.now()).getYears();
                return age >= 18;
            },"Must be at least 18")
            .bind(Student::getBirthdate, Student::setBirthdate);

        binder.forField(qualificationField).bind(Student::getQualification, Student::setQualification);
        // binder.forField(cvField).bind(Student::getCv, Student::setCv);
        // binder.forField(certsField).bind(Student::getCertificates, Student::setCertificates);
        //TODO bind uploaded data into Student

        layout.setColspan(genderField, 2);
        layout.setColspan(birthdateField, 2);
        layout.setColspan(qualificationField, 2);
        layout.setColspan(cvField, 2);

        passwordField.setVisible(false);

        layout.add(genderField, firstnameField,lastnameField, birthdateField);
        addForms();
        layout.add(qualificationField, cvField, certsField);

        binder.readBean(user);
    }

    @Override
    //Method for setting fields to read only
    public void setReadonly(boolean state) {
        
        genderField.setReadOnly(state);
        firstnameField.setReadOnly(state);
        lastnameField.setReadOnly(state);
        birthdateField.setReadOnly(state);

        emailField.setReadOnly(state);
        passwordField.setReadOnly(state);
        streetField.setReadOnly(state);
        zipField.setReadOnly(state);
        cityField.setReadOnly(state);
        countryField.setReadOnly(state);
        phoneField.setReadOnly(state);
        qualificationField.setReadOnly(state);
        cvField.setMaxFiles(state == true ? 0 : 1);
        certsField.setMaxFiles(state == true ? 0 : 10);
    }

    @Override
    //Method for setting fields to public
    public void setPublic(boolean state) {
        streetField.setVisible(state);
        zipField.setVisible(state);
        phoneField.setVisible(state);

        qualificationField.setVisible(state);
        cvField.setVisible(state);
        certsField.setVisible(state);
    }
}
