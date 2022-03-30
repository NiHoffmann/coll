package org.hbrs.se2.project.test;

import static org.junit.Assert.*;

import org.hbrs.se2.project.classes.User.Company;
import org.hbrs.se2.project.classes.User.Student;
import org.hbrs.se2.project.classes.User.User;
import org.hbrs.se2.project.control.Query;
import org.hbrs.se2.project.control.UserControl;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserControlTest {

    @Test
    public void checkIfExistsTest() throws SQLException {
        assertTrue(UserControl.checkIfExists("email@email.com"));
    }

    @Test
    public void getUserIDTest() throws Exception {
        assertEquals(2, UserControl.getUserID("email@email.com"));
    }

    @Test
    public void getUserTypeTest() throws Exception {
        assertEquals("Company", UserControl.getUserType(2));
    }

    @Test
    public void getUserInfoTest(){
        User user = UserControl.getUserInfo(2);
        int userid = user.getUserID();
        assertEquals(2, userid);
        assertEquals("email@email.com", user.getEmail());
        assertEquals("Hornstrasse 2", user.getStreet());
        assertEquals("50823", user.getZip());
        assertEquals("Cologne", user.getCity());
        assertEquals("Germany", user.getCountry());
        assertEquals("+49 221 17906100", user.getPhone());
        assertNull(user.getAvatar());
        assertEquals("Company", user.getUserType());
    }

    @Test
    public void getStudentInfoTest(){
        Student student = UserControl.getStudentInfo(4);
        int userid = student.getUserID();
        assertEquals(4, userid);
        assertEquals("Test@Test.com", student.getEmail());
        assertEquals("Test", student.getStreet());
        assertEquals("Test", student.getZip());
        assertEquals("Test", student.getCity());
        assertEquals("Germany", student.getCountry());
        assertEquals("Test", student.getPhone());
        assertNull(student.getAvatar());
        assertEquals("Student", student.getUserType());
        assertEquals("Test", student.getFirstname());
        assertEquals("Test", student.getLastname());
        assertEquals("Male", student.getGender());
        assertEquals("1979-12-31", student.getBirthdate().toString());
    }

    @Test
    public void getCompanyInfoTest(){
        Company company = UserControl.getCompanyInfo(2);
        int userid = company.getUserID();
        assertEquals(2, userid);
        assertEquals("email@email.com", company.getEmail());
        assertEquals("Hornstrasse 2", company.getStreet());
        assertEquals("50823", company.getZip());
        assertEquals("Cologne", company.getCity());
        assertEquals("Germany", company.getCountry());
        assertEquals("+49 221 17906100", company.getPhone());
        assertNull(company.getAvatar());
        assertEquals("Company", company.getUserType());
        assertEquals("Firma1", company.getName());
        assertFalse(company.getHiring());
    }

//    @Test
//    public void deleteUserTest() throws Exception {
//        Query.executeUpdate(
//                "INSERT INTO public.student (id, firstname, lastname, gender, birthdate) VALUES (99, firstname, lastname, gender, birthdate);" +
//                        "INSERT INTO public.company (id, name) VALUES (99, name);" +
//                        "INSERT INTO public.user (id, email, hash, street, zip, city, country, usertype) VALUES (99, email, hash, street, zip, city, country, usertype);"
//        );
//        UserControl.deleteUser(99);
//        assertNull(UserControl.getUserInfo(99));
//    }

}
