package org.hbrs.se2.project.test;

import static org.junit.Assert.*;
import org.hbrs.se2.project.classes.User.Application;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void createAndReadTest(){
        int id = 1;
        String forName = "Vorname";
        String lastName = "Nachname";
        int studentID = 1;
        int offerID = 2;
        String position = "Wichtige Position";
        String applicationText = "Hier Bewerbung";
        String status = "accepted";

        Application app1 = new Application(id, forName, lastName, studentID, offerID, position, applicationText, status);

        assertEquals(id, app1.getId());
        assertEquals(forName, app1.getForName());
        assertEquals(lastName, app1.getLastName());
        assertEquals(studentID, app1.getStudentId());
        assertEquals(offerID, app1.getOfferId());
        assertEquals(applicationText, app1.getApplicationText());
        assertEquals(position, app1.getPosition());
        assertEquals(status, app1.getStatus());


    }
}