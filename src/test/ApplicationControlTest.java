package org.hbrs.se2.project.test;

import static org.junit.Assert.*;
import org.hbrs.se2.project.control.ApplicationControl;
import org.hbrs.se2.project.control.Query;
import org.hbrs.se2.project.services.db.JDBCConnection;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationControlTest {
    JDBCConnection jdbc = new JDBCConnection();
    int oldid;
    int newid;

    @BeforeAll
    public void setUp() throws SQLException {
        JDBCConnection.connect();
        assertTrue(!JDBCConnection.getConnection().isClosed());
    }

    @Test
    public void applyTest() throws SQLException {
        int offerID = 0;
        int studentID = 0;
        String text = "";
        String status = "";
        int id = 0;

        ResultSet oldresult = Query.executeQuery("SELECT id FROM public.applications");
        while(oldresult.next()) {
            oldid = oldresult.getInt("id");
        }

        newid = oldid+1;

        ApplicationControl.apply(9, 9, "neue Bewerbung");

        ResultSet result = Query.executeQuery("SELECT offer_id, user_id, text, status, id FROM public.applications WHERE id = "+ newid +"");
        while(result.next()) {
            offerID = result.getInt("offer_id");
            studentID = result.getInt("user_id");
            text = result.getString("text");
            status = result.getString("status");
            id = result.getInt("id");
        }

        assertEquals(9, offerID);
        assertEquals(9, studentID);
        assertEquals("neue Bewerbung", text);
        assertEquals("new", status);
        assertEquals(oldid + 1, id);

    }

    @Test
    public void acceptStudentTest() throws SQLException {
        String status = "";
        ApplicationControl.acceptStudent(9);

        ResultSet result = Query.executeQuery("SELECT status FROM public.applications WHERE id = "+9+"");
        while(result.next()) {
            status = result.getString("status");
        }
        assertEquals("accepted", status);
    }

    @Test
    public void rejectStudentTest() throws SQLException{
        String status = "";
        ApplicationControl.rejectStudent(9);

        ResultSet result = Query.executeQuery("SELECT status FROM public.applications WHERE id = "+9+"");
        while(result.next()) {
            status = result.getString("status");
        }
        assertEquals("rejected", status);
    }

    @Test
    public void createJobOfferTest() throws SQLException {
        int offer_id = 0;
        int company_id = 0;
        String position = "";
        String description = "";
        int old_offer_id = 0;

        ResultSet oldresult = Query.executeQuery("SELECT offer_id FROM public.offers");
        while(oldresult.next()) {
            old_offer_id = oldresult.getInt("offer_id");
        }

        ApplicationControl.createJobOffer(9, "Neue Position", "Neue Beschreibung");

        int new_offer_id = old_offer_id + 1;
        ResultSet result = Query.executeQuery("SELECT offer_id, company_id, position, description FROM public.offers WHERE offer_id = "+new_offer_id+"");
        while(result.next()) {
            offer_id = result.getInt("offer_id");
            company_id = result.getInt("company_id");
            position = result.getString("position");
            description = result.getString("description");
        }

        assertEquals(new_offer_id, offer_id);
        assertEquals(9, company_id);
        assertEquals("Neue Position", position);
        assertEquals("Neue Beschreibung", description);

    }
}
