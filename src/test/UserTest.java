package org.hbrs.se2.project.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.se2.project.classes.User.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void userTest(){
        String street = "street";
        String zip = "zip";
        String city = "city";
        String country = "country";
        String phone = "phone";

        User user = new User(street, zip, city, country ,phone);

        assertEquals(street, user.getStreet());
        assertEquals(zip, user.getZip());
        assertEquals(city, user.getCity());
        assertEquals(country, user.getCountry());
        assertEquals(phone, user.getPhone());
    }

    @Test
    public void userSetTest(){
        String street = "street";
        String zip = "zip";
        String city = "city";
        String country = "country";
        String phone = "phone";

        String newstreet = "newstreet";
        String newzip = "newzip";
        String newcity = "newcity";
        String newcountry = "newcountry";
        String newphone = "newphone";

        int userID = 1;
        String email = "email";
        String password = "password";
        String avatar = "link";
        String userType = "Student";

        User user2 = new User(street, zip, city, country ,phone);

        user2.setStreet(newstreet);
        assertEquals(newstreet, user2.getStreet());
        user2.setZip(newzip);
        assertEquals(newzip, user2.getZip());
        user2.setCity(newcity);
        assertEquals(newcity, user2.getCity());
        user2.setCountry(newcountry);
        assertEquals(newcountry, user2.getCountry());
        user2.setPhone(newphone);
        assertEquals(newphone, user2.getPhone());

        user2.setUserID(userID);
        assertEquals(userID, user2.getUserID());
        user2.setEmail(email);
        assertEquals(email, user2.getEmail());
        user2.setPassword(password);
        assertEquals(password, user2.getPassword());
        user2.setAvatar(avatar);
        assertEquals(avatar, user2.getAvatar());
        user2.setUserType(userType);
        assertEquals(userType, user2.getUserType());
    }


}
