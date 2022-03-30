package org.hbrs.se2.project.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import org.hbrs.se2.project.classes.User.*;
import org.hbrs.se2.project.components.Form.CompanyRegister;
import org.hbrs.se2.project.components.Form.StudentRegister;
import org.hbrs.se2.project.control.SessionControl;
import org.hbrs.se2.project.control.UserControl;


@Route(value = "register")
@RouteAlias(value = "register")
public class RegisterView extends Div implements BeforeEnterObserver {
    String mode = "Student";

    public RegisterView() {
        getStyle().set("padding-top", "1px").set("padding-left", "10px").set("padding-right", "10px");
        setSizeFull();
        getElement().getThemeList().add("dark");

        H2 header = new H2("Register");

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Select account type");
        radioGroup.setItems("Student", "Business");

        Div formContent = new Div();
        formContent.getElement().getThemeList().add("dark");
        formContent.getElement().getStyle().set("padding", "10px");

        final StudentRegister studentRegister = new StudentRegister();
        studentRegister.setVisible(false);
        final CompanyRegister companyRegister = new CompanyRegister();
        companyRegister.setVisible(false);

        formContent.add(studentRegister, companyRegister);
        
        radioGroup.addValueChangeListener(event -> {
            mode = event.getValue();

            if (mode == "Student") {
                companyRegister.setVisible(false);
                studentRegister.setVisible(true);
            }
            else if (mode == "Business") {
                studentRegister.setVisible(false);
                companyRegister.setVisible(true);
            }
        });

        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        registerButton.addClickListener(event -> {
            registerButton.setEnabled(false);
            if (mode == "Student") {
                try {
                    Student user = studentRegister.getUser();
                    studentRegister.binder.writeBean(user);
                    studentRegister.setReadonly(true);
                    user.setUserType("Student");

                    UserControl.register(user);

                    Notification note = Notification.show("Saved details");
                    note.setPosition(Position.TOP_CENTER);
                    note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    note.setDuration(3000);

                    UI.getCurrent().navigate(Home.class);
                }
                catch (ValidationException e) {

                }
                catch (Exception e) {
                    e.printStackTrace();

                    Notification note = Notification.show(e.getMessage());
                    note.setPosition(Position.TOP_CENTER);
                    note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    note.setDuration(5000);

                    studentRegister.setReadonly(false);
                    registerButton.setEnabled(true);
                }
            }
            else if (mode == "Business") {
                try {
                    Company user = companyRegister.getUser();
                    companyRegister.binder.writeBean(user);
                    companyRegister.setReadonly(true);
                    user.setUserType("Company");

                    UserControl.register(user);

                    Notification note = Notification.show("Success");
                    note.setPosition(Position.TOP_CENTER);
                    note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    note.setDuration(3000);

                    UI.getCurrent().navigate(Home.class);
                }
                catch (ValidationException e) {

                }
                catch (Exception e) {
                    e.printStackTrace();

                    Notification note = Notification.show(e.getMessage());
                    note.setPosition(Position.TOP_CENTER);
                    note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    note.setDuration(5000);

                    companyRegister.setReadonly(false);
                    registerButton.setEnabled(true);
                }
            }
        });

        add(header, radioGroup, formContent, registerButton);

        radioGroup.setValue("Student");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SessionControl.authenticateSession()) {
            event.forwardTo("home");
        }
    }
}
