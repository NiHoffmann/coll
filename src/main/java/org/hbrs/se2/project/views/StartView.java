package org.hbrs.se2.project.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import org.hbrs.se2.project.components.CookieConsent;
import org.hbrs.se2.project.control.CookieControl;
import org.hbrs.se2.project.control.SessionControl;
import org.hbrs.se2.project.control.UserControl;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer die Startseite von Coll an.
 */
@Route(value = "")
@RouteAlias(value = "star")
public class StartView extends VerticalLayout{
    
    final Button[] buttons;

    public StartView() {
        getElement().getThemeList().add("dark");
        this.getElement().getStyle().set("padding-left", "0px");
        //this.getElement().getStyle().set("display", "flex").set("align-items", "stretch");

        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.getElement().getThemeList().add("dark");
        topMenu.getElement().getStyle()
            .set("max-height", "30px")
            .set("padding-left", "10px")
            .set("padding-right", "10px");

        HorizontalLayout topMenuSub = new HorizontalLayout();
        topMenuSub.getElement().getThemeList().add("dark");
        topMenuSub.getStyle()
            .set("position", "absolute")
            .set("right", "0");
        Button aboutButton = new Button("About", event -> {
            UI.getCurrent().navigate(About.class);
        });

        //Check if the user is logged in or not
        if (SessionControl.authenticateSession()) {
            Button logoutButton = new Button("Logout", event -> {
                UserControl.logout();
                UI ui = this.getUI().get();
                ui.getSession().close();
                ui.getPage().setLocation("/");
            });

            Button profileButton = new Button("Profile", event -> {
                UI.getCurrent().navigate(Home.class);
            });

            buttons = new Button[] {
                aboutButton, logoutButton, profileButton
            };

            topMenuSub.add(logoutButton, profileButton);
        }
        else {
            Button registerButton = new Button("Register", event -> {
                UI.getCurrent().navigate(RegisterView.class);
            });

            Button loginButton = new Button("Login", event -> {
                UI.getCurrent().navigate(LoginView.class);
            });

            buttons = new Button[] {
                registerButton, loginButton, aboutButton
            };

            topMenuSub.add(loginButton, registerButton);
        }
        
        Div body = new Div();
        body.getStyle().set("width", "100%");
        body.getElement().getThemeList().add("dark");

        topMenu.add(aboutButton, topMenuSub);
        add(topMenu, body);

        if (CookieControl.getCookieByName("AskedConsent") == null) {
            CookieConsent consent = new CookieConsent();
            consent.getElement().getStyle()
                .set("position", "fixed")
                .set("height", "150px")
                .set("bottom", "0px")
                .set("margin-left", "0px");

            add(consent);
        }

        for (Button button : buttons) {
            connectButton(button);
        }
    }

    private void onHover(Button button) {
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    }

    private void onExit(Button button) {
        button.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);

    }

    private void connectButton(Button button) {
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.getElement().getStyle()
            .set("margin-left", "20px")
            .set("margin-right", "20px");

        button.getElement().addEventListener("mouseover", event -> {
            onHover(button);
        });

        button.getElement().addEventListener("mouseleave", event -> {
            onExit(button);
        });
    }
}
