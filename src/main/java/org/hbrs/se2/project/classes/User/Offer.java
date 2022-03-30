package org.hbrs.se2.project.classes.User;

public class Offer {
    private int id;
    private Company company;
    private String position;
    private String description;

    public Offer(int id, Company company, String position, String description){
        this.company = company;
        this.position = position;
        this.description = description;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Company getCompany(){
        return company;
    }   

    public String getName(){
        return company.getName();
    }

    public String getPosition(){
        return position;
    }

    public String getDescription(){
        return description;
    }
}
