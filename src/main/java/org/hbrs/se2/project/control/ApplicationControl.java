package org.hbrs.se2.project.control;

import java.sql.SQLException;

public class ApplicationControl {
    public static void acceptStudent(int applicationID) {
        try {
            Query.executeQuery("UPDATE public.applications SET status = 'accepted' WHERE id = "+applicationID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void rejectStudent(int applicationID) {
        try {
            Query.executeQuery("UPDATE public.applications	SET status = 'rejected' WHERE id = "+applicationID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void apply(int offerID, int studentID, String text) throws SQLException {
        Query.executeUpdate("INSERT INTO public.applications VALUES ("+studentID+","+offerID+",'"+text+"','new')");
    }

    public static void createJobOffer(int companyID, String jobType, String description) throws SQLException {
        Query.executeUpdate("INSERT INTO public.offers (company_id, position, description) VALUES (" + companyID + ",'" + jobType + "', '" + description + "')");
    }
}
