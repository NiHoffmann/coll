package org.hbrs.se2.project.control;

import org.hbrs.se2.project.services.db.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Static class to handle SQL related stuff
 */
public class Query {
    /**
     * Used to run sql statements
     * @param query
     * @return Use .get()
     * @throws SQLException
     * @throws NullPointerException
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        JDBCConnection.connect();

        ResultSet set;

        try {
            Statement statement = JDBCConnection.getConnection().createStatement();
            set = statement.executeQuery(query);

            System.out.println("QUERY SUCCESS: " + query);
        }
        catch (SQLException e) {
            System.out.println("QUERY FAIL: " + query);
            throw e;
        }
        finally {
            JDBCConnection.close();
        }

        return set;
    }

    public static Integer executeUpdate(String query) throws SQLException {
        JDBCConnection.connect();

        Integer key = null;

        try {
            Statement statement = JDBCConnection.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("UPDATE SUCCESS: " + query);

            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                key = result.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("UPDATE FAIL: " + query);
            throw e;
        }
        finally {
            JDBCConnection.close();
        }

        return key;
    }
    
}
