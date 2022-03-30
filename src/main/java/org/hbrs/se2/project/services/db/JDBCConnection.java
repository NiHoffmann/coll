package org.hbrs.se2.project.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import org.hbrs.se2.project.services.db.exceptions.DatabaseLayerException;


//Class for connecting to the database
public class JDBCConnection {

    private static String dbURL = "jdbc:postgresql://dumbo.inf.h-brs.de/rschwi2s";
    private static String user = "rschwi2s";
    private static String pass = "rschwi2s";

    private static Connection connection = null;
    //Connect to given database
    public static boolean connect() {
        try {
            connection = DriverManager.getConnection(dbURL, user, pass);
            connection.setSchema("public");

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }
    //Close database connection
    public static boolean close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public static Connection getConnection(){
        return connection;
    }

}