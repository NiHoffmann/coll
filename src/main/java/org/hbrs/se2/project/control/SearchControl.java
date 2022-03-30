package org.hbrs.se2.project.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.hbrs.se2.project.classes.User.Application;
import org.hbrs.se2.project.classes.User.Company;
import org.hbrs.se2.project.classes.User.Offer;
import org.hbrs.se2.project.classes.User.Student;
//Class for handling search operations
public class SearchControl {

    public static ArrayList<Application> searchApplicants(String search, int companyID) throws SQLException {
        ArrayList<Application> data = new ArrayList<Application>();

        ResultSet result = Query.executeQuery("SELECT a.id , a.user_id, a.offer_id, a.text, a.status , b.company_id, b.position, c.firstname, c.lastname FROM public.applications as a LEFT JOIN public.offers as b on a.offer_id = b.offer_id LEFT JOIN public.student as c on a.user_id = c.id");
        while(result.next()) {
            String firstName = result.getString("firstname");
            String lastName = result.getString("lastname");
            int studentID = result.getInt("user_id");
            int offerID = result.getInt("offer_id");
            String position = result.getString("position");
            String applicationText = result.getString("text");
            String status = result.getString("status");
            int id = result.getInt("id");

            int offerCompanyID = result.getInt("company_id");

            if((companyID == -1 || offerCompanyID == companyID)  && (search == null || (firstName+lastName+position+applicationText+status).trim().toLowerCase().contains(search.toLowerCase()))){
                data.add(new Application(id, firstName,lastName,studentID,offerID,position,applicationText, status));
            }
                
        }
        
        return data;
    }

    public static ArrayList<Offer> searchOffer(String search) throws SQLException {
        ArrayList<Offer> data = new ArrayList<Offer>();
        ResultSet result = Query.executeQuery("SELECT a.offer_id , a.position, a.description,b.id, b.name, c.street, c.zip,  c.city, c.country FROM public.offers as a LEFT JOIN public.company as b on a.company_id = b.id LEFT JOIN public.user as c on b.id = c.id");
        while(result.next()) {
            int offer_id = result.getInt("offer_id");
            String position = result.getString("position");
            String description = result.getString("description");
            String name = result.getString("name");
            String street = result.getString("street");
            String zip = result.getString("zip");
            String city = result.getString("city");
            String country = result.getString("country");


            if (search == null || (position + description + name + street + zip + city + country ).toLowerCase().contains(search.toLowerCase().trim())) {
                Company company = new Company(name, street, zip, city, country, "0");
                Offer offer = new Offer(offer_id, company, position, description);

                data.add(offer);
            }
        }
        
        return data;
    }

    public static ArrayList<Student> searchStudent(String search) throws SQLException {
        ArrayList<Student> data = new ArrayList<Student>();

        ResultSet result = Query.executeQuery("SELECT a.id, a.firstname, a.lastname, a.gender, a.birthdate, b.email, b.street, b.zip, b.city, b.country, b.phone FROM public.student as a LEFT JOIN public.user as b ON a.id = b.id");
        while(result.next()) {
            int id = result.getInt("id");
            String firstname = result.getString("firstname");
            String lastname = result.getString("lastname");
            
            if ((firstname + lastname).toLowerCase().contains(search.toLowerCase().trim())) {
                Student student = new Student();
                student.setUserID(id);
                student.setEmail(result.getString("email"));
                student.setStreet(result.getString("street"));
                student.setZip(result.getString("zip"));
                student.setCity(result.getString("city"));
                student.setCountry(result.getString("country"));

                student.setFirstname(firstname);
                student.setLastname(lastname);
                student.setGender(result.getString("gender"));
                student.setBirthdate(LocalDate.parse(result.getString("birthdate")));
                student.setPhone(result.getString("phone"));
                
                data.add(student);
            }
        }
        
        return data;
    }

    public static ArrayList<Company> search(String search) throws SQLException {
        ArrayList<Company> data = new ArrayList<Company>();

        ResultSet result = Query.executeQuery("SELECT a.id, a.name, b.street, b.zip,  b.city, b.country FROM public.company as a LEFT JOIN public.user as b ON a.id = b.id");

        while(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String street = result.getString("street");
            String zip = result.getString("zip");
            String city = result.getString("city");
            String country = result.getString("country");

            if (search == null || (name+street+zip+city+country).toLowerCase().contains(search.toLowerCase().trim())) {
                Company company = new Company( name,  street,  zip,  city,  country,  "0");
                company.setUserID(id);
                data.add(company);
            }
        }   

        return data;
    }
}
