package org.hbrs.se2.project.classes.User;

public class Application {
    private int id;
    private String forName;
    private String lastName;
    private int studentID;
    private int offerID;
    private String position;
    private String applicationText;
    private String status;
    
    public Application(int id ,String forName, String lastName, int studentID, int offerID,String position, String applicationText, String status){
        this.id = id;
        this.forName = forName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.offerID = offerID;
        this.position = position;
        this.applicationText = applicationText;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public String getLastName(){
        return lastName;
    }

    public String getForName(){
        return forName;
    }

    public int getStudentId(){
        return studentID;
    }

    public int getOfferId(){
        return offerID;
    }

    public String getApplicationText(){
        return applicationText;
    }

    public String getPosition(){
        return position;
    }

    public String getStatus(){
        return status;
    }
}
