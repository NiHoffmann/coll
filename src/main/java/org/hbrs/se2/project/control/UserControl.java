package org.hbrs.se2.project.control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.Cookie;

import org.hbrs.se2.project.classes.User.*;

public class UserControl {

    public static boolean checkIfExists(String email) throws SQLException {
        ResultSet set = Query.executeQuery("SELECT id FROM public.user WHERE email = '" + email + "'");
        return set.next();
    }

    /**
     * tries to retriev the userID associated with given email ,throws if unsuccessful.
     * 
     * @param email
     * @return
     * @throws Exception
     */
    public static int getUserID(String email) throws Exception {
        ResultSet set = Query.executeQuery("SELECT id FROM public.user WHERE email = '" + email + "'");

        if (set.next())
           return set.getInt("id"); 
        
        throw new NullPointerException("User does not exist");
    }

    //TODO
    public static void updateUser(Student user) throws SQLException {

    }

    public static void updateUser(Company user) throws SQLException {

    }

    /**
     * tries to retriev the userType associated with given id ,throws if unsuccessful.
     * @param id
     * @return
     * @throws Exception
     */
    public static String getUserType(int id) throws Exception {
        ResultSet set = Query.executeQuery("SELECT usertype FROM public.user WHERE id = '" + id + "'");

        if (!set.next())
            throw new Exception("User does not exist");

        return set.getString("usertype");
    }

    public static User getUserInfo(int userID) {
        try {
            ResultSet userSet = Query.executeQuery(
                "SELECT * FROM public.user as u WHERE u.id = '" + userID +"'"
            );

            if (!userSet.next()) {
                return null;
            }

            User user = new Student();
            user.setUserID(userID);
            user.setEmail(userSet.getString("email"));
            user.setStreet(userSet.getString("street"));
            user.setZip(userSet.getString("zip"));
            user.setCity(userSet.getString("city"));
            user.setCountry(userSet.getString("country"));
            user.setPhone(userSet.getString("phone"));
            user.setAvatar(userSet.getString("avatar"));
            user.setUserType(userSet.getString("usertype"));

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Student getStudentInfo(int userID) {
        try {
            ResultSet userSet = Query.executeQuery(
                "SELECT * FROM public.user as u WHERE u.id = '" + userID +"'"
            );

            if (!userSet.next()) {
                return null;
            }

            String userType = userSet.getString("usertype");
            String email = userSet.getString("email");
            String street = userSet.getString("street");
            String zip = userSet.getString("zip");
            String city = userSet.getString("city");
            String country = userSet.getString("country");
            String phone = userSet.getString("phone");
            String avatar = userSet.getString("avatar");

            ResultSet studentSet = Query.executeQuery(
                "SELECT * FROM public.student as u WHERE u.id = '" + userID +"'"
            );

            if (!studentSet.next()) {
                return null;
            }

            Student user = new Student();
            user.setUserID(userID);
            user.setEmail(email);
            user.setStreet(street);
            user.setZip(zip);
            user.setCity(city);
            user.setCountry(country);
            user.setPhone(phone);
            user.setAvatar(avatar);
            user.setUserType(userType);

            // Student fields
            user.setFirstname(studentSet.getString("firstname"));
            user.setLastname(studentSet.getString("lastname"));
            user.setGender(studentSet.getString("gender"));
            user.setBirthdate(LocalDate.parse(studentSet.getString("birthdate")));

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Company getCompanyInfo(int userID) {
        Company user = null;
        
        try {
            ResultSet userSet = Query.executeQuery(
                "SELECT * FROM public.user as u WHERE u.id = '" + userID +"'"
            );

            if (!userSet.next()) {
                return null;
            }

            String userType = userSet.getString("usertype");
            String email = userSet.getString("email");
            String street = userSet.getString("street");
            String zip = userSet.getString("zip");
            String city = userSet.getString("city");
            String country = userSet.getString("country");
            String phone = userSet.getString("phone");
            String avatar = userSet.getString("avatar");

            ResultSet companySet = Query.executeQuery(
                "SELECT * FROM public.company as u WHERE u.id = '" + userID +"'"
            );

            if (!companySet.next()) {
                return null;
            }

            user = new Company();
            user.setUserID(userID);
            user.setEmail(email);
            user.setStreet(street);
            user.setZip(zip);
            user.setCity(city);
            user.setCountry(country);
            user.setPhone(phone);
            user.setAvatar(avatar);
            user.setUserType(userType);

            // Company fields
            user.setName(companySet.getString("name"));
            user.setHiring(companySet.getBoolean("hiring"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Attempts to register a student, throws if unsuccessful.
     * @param user
     * @throws Exception
     */
    public static Cookie register(Student user) throws Exception {
        if (checkIfExists(user.getEmail()))
            throw new SQLException("Account already exists");

        String email = user.getEmail();
        String hash = hashPassword(user.getPassword());
        String street = user.getStreet();
        String zip  = user.getZip();
        String city = user.getCity();
        String country = user.getCountry();
        String phone = user.getPhone();
        String userType = user.getUserType();
            
        Query.executeUpdate(
            "INSERT INTO public.user (email, hash, street, zip, city, country, phone, usertype) VALUES ('"+ email +"', '"+hash+"', '"+street+"', '"+zip+"', '"+city+"', '"+country+"', '"+phone+"', '"+userType+"')"
        );

        int userID = getUserID(email);
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String gender = user.getGender();
        String birthdate = user.getBirthdate().toString();

        Query.executeUpdate(
            "INSERT INTO public.student (id, firstname, lastname, gender, birthdate) VALUES ('"+ userID +"', '"+firstname+"', '"+lastname+"', '"+gender+"', '"+birthdate+"')"
        );

        return SessionControl.createLoginSession(userID);
    }

    public static Cookie register(Company user) throws Exception {
        if (checkIfExists(user.getEmail())) {
            throw new SQLException("Account already exists");
        }
        
        String email = user.getEmail();
        String hash = hashPassword(user.getPassword());
        String street = user.getStreet();
        String zip  = user.getZip();
        String city = user.getCity();
        String country = user.getCountry();
        String phone = user.getPhone();
        String userType = user.getUserType();

        Query.executeUpdate(
            "INSERT INTO public.user (email, hash, street, zip, city, country, phone, usertype) VALUES ('"+ email +"', '"+hash+"', '"+street+"', '"+zip+"', '"+city+"', '"+country+"', '"+phone+"', '"+userType+"')"
        );

        int userID = getUserID(email);
        String name = user.getName();

        Query.executeUpdate(
            "INSERT INTO public.company (id, name) VALUES ('"+ userID +"', '"+ name +"')"
        );

        return SessionControl.createLoginSession(userID);
    }

    /**
     * login user function 
     * throws exception if either password or email cant be found in db (with according text)
     * otherwise the login is successful and creates a cookie
     *
     * @param email
     * @param password
     * @throws Exception
     */

    public static Cookie login(String email, String password) throws Exception {
        ResultSet emailSet = Query.executeQuery("SELECT id FROM public.user WHERE email = '" + email + "'");

        if (!emailSet.next()) {
            throw new Exception("Email address not found");
        }
        ResultSet passwordSet = Query.executeQuery("SELECT id FROM public.user WHERE hash = '" + hashPassword(password) + "'");
        if (!passwordSet.next()) {
            throw new Exception("Invalid password");
        }

        return SessionControl.createLoginSession(emailSet.getInt("id"));
    }

    public static void logout() {
        SessionControl.quitSession();
    }


    /**
     * Attempts to hash a String (password) and returns it as a HEX representation
     * throws NoSuchAlgorithmException if SHA3-256 HashingAlgorithm isnt availabe
     * 
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String shaAsHex = "";
        
        for(int i=0;i<hashbytes.length;i++){
            shaAsHex += Integer.toHexString(hashbytes[i]);
        }
        
        return shaAsHex;
    }

    public static void deleteUser(int userID) throws Exception {
        Query.executeUpdate(
                "DELETE FROM public.student WHERE id = '"+ userID +"';" +
                        "DELETE FROM public.company WHERE id = '"+ userID +"';" +
                        "DELETE FORM public.user WHERE id = '"+ userID +"';"
        );
    }
    
}
