package org.hbrs.se2.project.classes;

import org.hbrs.se2.project.classes.User.User;
//Class for session handling with ids
public class Session {
    static Integer currentUser = 1;
    //Returns the id of the current user
    public static Integer getCurrentUser() {
        return currentUser;
    }
    //Sets a user id for the current user if one isn't assigned yet
    public static <T extends User> void setCurrentUser(Integer userID) {
        if (currentUser == null)
            Session.currentUser = userID;
    }
}
