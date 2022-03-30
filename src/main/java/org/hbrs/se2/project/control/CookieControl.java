package org.hbrs.se2.project.control;

import javax.servlet.http.Cookie;

import com.vaadin.flow.server.VaadinService;

/**
 * Static class for creating, and managing cookies
 */
public class CookieControl {
    private CookieControl() {}

    public static Cookie createCookie(String name, String value, int lifetime) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(lifetime);

        return cookie;
    }

    /**
     * Refreshes a cookies lifetime
     * @param cookie
     */
    public static void renewCookie(Cookie cookie) {
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    /**
     * Deletes a cookie from the client
     */
    public static void removeCookie(Cookie cookie) {
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    /**
     * Returns a cookie with the specific name, null if none found
     * @param name
     * @return ?Cookie
     */
    public static Cookie getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies == null) {return null;}

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }

        return null;
    }
}