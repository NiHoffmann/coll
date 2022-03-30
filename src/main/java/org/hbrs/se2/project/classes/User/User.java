package org.hbrs.se2.project.classes.User;

//Class for creating a user object which is the basis for company and student objects
public class User {
    private Integer userID;
    private String email;
    private String password;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String phone;
    private String avatar;
    private String userType;
    //Blank constructor
    public User() {
        
    }
    //Constructor for creating a user object with an address (including street, zip code, city and country) and phone number
    public User(String street, String zip, String city, String country, String phone) {
        this();

        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
