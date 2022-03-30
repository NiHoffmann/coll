package org.hbrs.se2.project.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.hbrs.se2.project.classes.User.Student;
import org.junit.jupiter.api.Test;

public class StudentTest {
    private String firstname = "firstname";
    private String lastname = "lastname";
    private String gender = "n";
    private LocalDate birthdate = LocalDate.now();
    private String qualification = "qualifications";
    private String cv = "cv";
    private String street = "Street";
    private String zip = "zip";
    private String city = "city";
    private String country = "country";
    private String phone = "phone";

    @Test
    public void constructorLongTest(){
        Student student = new Student(firstname,lastname ,gender , birthdate,qualification,cv,street , zip,city,country,phone);

        assertEquals(firstname, student.getFirstname());
        assertEquals(lastname, student.getLastname());
        assertEquals(gender, student.getGender());
        assertEquals(birthdate, student.getBirthdate());
        assertEquals(qualification, student.getQualification());
        assertEquals(cv, student.getCv());
        assertEquals(street, student.getStreet());
        assertEquals(city, student.getCity());
        assertEquals(country, student.getCountry());
        assertEquals(phone, student.getPhone());
    }

    @Test
    public void constructorShortTest(){

        Student student = new Student(firstname,lastname ,gender , birthdate,qualification,cv );

        assertEquals(firstname, student.getFirstname());
        assertEquals(lastname, student.getLastname());
        assertEquals(gender, student.getGender());
        assertEquals(birthdate, student.getBirthdate());
        assertEquals(qualification, student.getQualification());
        assertEquals(cv, student.getCv());
    }


}
