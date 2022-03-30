package org.hbrs.se2.project.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.se2.project.classes.User.Company;
import org.hbrs.se2.project.classes.User.Offer;
import org.junit.jupiter.api.Test;

public class OfferTest {

    @Test
    public void constructorTest(){
        int id = 1;
        String companyName = "CompanyName";
        Company company = new Company(companyName);
        String position = "position";
        String description = "description";

        Offer offer = new Offer(id, company , position, description);

        assertEquals(id, offer.getId());
        assertEquals(companyName, company.getName());
        assertEquals(position, offer.getPosition());
        assertEquals(description, offer.getDescription());
    }

}
