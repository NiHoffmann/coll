package org.hbrs.se2.project.classes.User;

import java.time.LocalDate;
//Class for creating a student object
public class Student extends User {
    private String firstname;
    private String lastname;
    private String gender;
    private LocalDate birthdate;
    private String cv;
    private String[] certificates;
    private String qualification;

    //Blank constructor
    public Student() {
        
    }

    //Constructor for Student fields
    public Student(String firstname, String lastname, String gender, LocalDate birthdate, String qualification, String cv ) {
        this();

        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.qualification = qualification;
        this.cv = cv;
    }

    //Constructor for Student fields + User fields
    public Student(String firstname, String lastname, String gender, LocalDate birthdate, String qualification, String cv, String street, String zip, String city, String country, String phone) {
        super(street, zip, city, country, phone);
        
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.qualification = qualification;
        this.cv = cv;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String[] getCertificates() {
        return certificates;
    }

    public void setCertificates(String[] certificates) {
        this.certificates = certificates;
    }
}
