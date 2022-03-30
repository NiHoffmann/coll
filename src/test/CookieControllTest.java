package org.hbrs.se2.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.Cookie;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;

import org.hbrs.se2.project.control.CookieControl;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;




public class CookieControllTest {

    String name = "name"; 
    String value = "abcdefg";
    int lifeTime = 1000;
    Cookie cookie = CookieControl.createCookie(name , value , lifeTime);

    @Test
    public void testCreate(){
        CollApplication.main(null);

        assertEquals(cookie.getName(), name);
        assertEquals(cookie.getValue(), value);
        assertEquals(cookie.getMaxAge(), lifeTime);
    }

    @Test
    public void testRenew(){
        assertTrue(true);
    }

    @Test
    public void testRemove(){
        assertTrue(true);
    }

    @Test void getCookieByNameTest(){
        assertTrue(true);
    }
}
