package org.hbrs.se2.project.test;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.hbrs.se2.project.services.db.JDBCConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class JDBCConnectionTest {

    @BeforeAll
    public void testOpen() throws SQLException{
        JDBCConnection.connect();
        assertTrue(!JDBCConnection.getConnection().isClosed());
    }

    @AfterAll
    public void testClose() throws SQLException{
        JDBCConnection.close();
        assertTrue(JDBCConnection.getConnection().isClosed());
    }

}
