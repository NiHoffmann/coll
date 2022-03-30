package org.hbrs.se2.project.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.vaadin.flow.server.VaadinService;

import org.hbrs.se2.project.classes.User.User;

public class SessionControl {
    public static User myData;
    
    private SessionControl() {}

    /**
     * Creates a login session by creating a cookie with a UUID, the database entry: id = userID, uuid = UUID, timestamp = UNIX seconds
     * @param userID
     * @throws SQLException
     */
    public static Cookie createLoginSession(int userID) throws SQLException {
        String uniqueSessionID = UUID.randomUUID().toString();
        int age = 60 * 60;
        String path = VaadinService.getCurrentRequest().getContextPath();
        System.out.println("COOKIE PATH " + path);
        
        Cookie sessionCookie = new Cookie("session", uniqueSessionID);
        sessionCookie.setMaxAge(age);
        sessionCookie.setPath(path);

        VaadinService.getCurrentResponse().addCookie(sessionCookie);

        try {
            quitSessionFromDB(userID);

            int currentTime = ((int)(long)(System.currentTimeMillis() / 1000));
            Query.executeUpdate(
                "INSERT INTO public.session (id, uuid, timestamp) VALUES ('" + userID + "', '" + uniqueSessionID + "', '" + currentTime + "')"
            );
        } catch (SQLException e) {
            CookieControl.removeCookie(sessionCookie);
            throw e;
        }

        User data = new User();
        data.setUserID(userID);
        data.setUserType("Student");
        myData = data;

        try {
            ResultSet set = Query.executeQuery(
                "SELECT u.usertype FROM public.session AS s LEFT JOIN public.user as u ON s.uuid = '" + sessionCookie.getValue() + "' AND u.id = s.id"
            );

            if (set.next()) {
                //TODO ghetto fix
                while (set.getString("usertype") == null) {
                    set.next();
                }
                myData.setUserType(set.getString("usertype"));
            }
            else {
                System.out.println("FAILED TO GET USERTYPE");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sessionCookie;
    }

    /**
     * Authenticates the login session by fetching the "session" cookie and checking if its value matches the hash from the DB
     * Any exception thrown will result in the return value being false
     */
    public static boolean authenticateSession() {
        boolean authenticated = false;
        Cookie sessionCookie = CookieControl.getCookieByName("session");

        if (sessionCookie == null) {
            return false;
        }
        try {
            // Select all from user where session.id == user.id and session.hash == cookie.hash
            ResultSet set = Query.executeQuery(
                "SELECT u.id, u.usertype FROM public.session AS s LEFT JOIN public.user as u ON s.uuid = '" + sessionCookie.getValue()+ "' AND u.id = s.id"
            );

            authenticated = set.next();

            //TODO ghetto fix
            while (set.getString("usertype") == null) {
                set.next();
            }
            
            User data = new User();
            data.setUserID(set.getInt("id"));
            data.setUserType(set.getString("usertype"));
            myData = data;

        } catch (SQLException e) {
            System.out.println("FAILED TO AUTHENTICATE SESSION");
            e.printStackTrace();
        }

        return authenticated;
    }

    /**
     * Gets the userID by querying the id from the "session" cookies value in the DB
     * @return -1 if an exception occured or if the the session is invalid
     */
    public static int getUserIDFromSession() {
        Cookie sessionCookie = CookieControl.getCookieByName("session");
        if (sessionCookie == null) {
            System.out.println("COULD NOT FIND COOKIE");
            return -1;
        }

        int userID = -1;

        try {
            ResultSet set = Query.executeQuery(
                "SELECT id FROM public.session WHERE uuid = '" + sessionCookie.getValue() + "'"
            );

            if (set.next()) {
                userID = set.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

    /**
     * The logout method basically
     */
    public static void quitSession() {
        Cookie sessionCookie = CookieControl.getCookieByName("session");
        myData = null;

        if (sessionCookie == null) {
            return;
        }

        String uuid = sessionCookie.getValue();

        //Remove cookie locally
        CookieControl.removeCookie(sessionCookie);

        //Remove cookie from DB
        try {
            //Blindly assuming that the session is in the DB to reduce DB calls
            Query.executeUpdate("DELETE FROM public.session WHERE uuid = '" + uuid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void refreshSessionCookie(Cookie cookie) {
        
    }

    public static void refreshSession(int userID) throws SQLException {
        Cookie sessionCookie = CookieControl.getCookieByName("session");

        if (sessionCookie == null) {
            createLoginSession(userID);
        }
        else {
            refreshSessionCookie(sessionCookie);
        }
    }

    /**
     * Removes a session from the DB by force.
     * Used to get rid of a session when the user did not log out but his cookie expired
     * @param userID
     */
    public static void quitSessionFromDB(int userID) {
        try {
            Query.executeUpdate("DELETE FROM public.session WHERE id = '" + userID + "'");
        } catch (Exception e) {}
    }

    /**
     * Used to delete ALL sessions where their creation time is older than 60 minutes, not used due to quitSessionFromDB
     */
    public static void quitOldSessionsFromDB() {
        int currentTime = ((int)(long)(System.currentTimeMillis() / 1000));

        try {
            Query.executeUpdate("DELETE FROM public.session WHERE " + currentTime + " - timestamp >= 3600");
        } catch (Exception e) {}
    }
}