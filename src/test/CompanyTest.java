package org.hbrs.se2.project.test;

import org.hbrs.se2.project.classes.User.Application;
import org.hbrs.se2.project.classes.User.Company;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.hbrs.se2.project.classes.User.Application;
import org.junit.Test;

public class CompanyTest {

    @Test
    public void createAndReadTest(){

        Company comp = new Company("Name");

        assertEquals("Name", comp.getName());
        comp.setName("NewName");
        assertEquals("NewName", comp.getName());
        comp.setHiring(true);
        assertEquals(true, comp.getHiring());



    }
}