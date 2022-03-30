package org.hbrs.se2.project.classes.User;

//Class for creating a company object
public class Company extends User {
    String name;
    boolean hiring;

    public Company() {
        
    }
    
    public Company(String name) {
        this();

        this.name = name;
    }
    
    public Company(String name, String street, String zip, String city, String country, String phone) {
        super(street, zip, city, country, phone);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public boolean getHiring() {
        return hiring;
    }
    public void setHiring(boolean hiring) {
        this.hiring = hiring;
    }
}
