package org.hbrs.se2.project.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.RouteAlias;

import org.hbrs.se2.project.components.Form.LoginForm;
import org.hbrs.se2.project.control.SessionControl;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@RouteAlias(value = "login")
public class LoginView extends Div implements BeforeEnterObserver {
    public LoginView() {
        getStyle().set("margin-top", "0px").set("padding-top", "0px");
        this.getElement().getThemeList().add("dark");
        setSizeFull();

        Div body = new Div();
        body.getElement().getThemeList().add("dark");
        body.getStyle()
            .set("outline-style", "solid")
            .set("outline-width", "2px")
            .set("outline-color", "var(--lumo-primary-color)")
            .set("border-radius", "10px")
            .set("padding-left", "10px")
            .set("padding-right", "10px")
            .set("padding-bottom", "10px")
            .set("background-color", "hsl(214, 40%, 25%)")
            .set("position", "fixed")
            .set("top", "50%")
            .set("left", "50%")
            .set("-webkit-transform", "translate(-50%, -50%)")
            .set("transform", "translate(-50%, -50%)");

        H3 header = new H3("Log in");
        LoginForm loginForm = new LoginForm();

        loginForm.onSuccess = () -> {
            getUI().ifPresent(action -> {
                action.navigate(Home.class);
            });
        };

        loginForm.onError = (Exception e) -> {
            body.getStyle().set("outline-color", "var(--lumo-error-color)");
        };

        loginForm.getStyle().set("background-color", body.getStyle().get("background-color"));
        loginForm.getFormLayout().getStyle().set("background-color", body.getStyle().get("background-color"));

        body.add(header, loginForm);
        add(body);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SessionControl.authenticateSession()) {
            event.forwardTo("home");
        }
    }
}

