package org.hbrs.se2.project.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;

import org.hbrs.se2.project.control.CookieControl;
import org.hbrs.se2.project.views.Terms;

//Class for displaying cookie banner
public class CookieConsent extends Div {
    final H3 header;
    final Paragraph text;
    final Button termsButton;
    final Button acceptButton;

    public CookieConsent() {
        setWidth("100%");
        getElement().getStyle()
            .set("background-color", "rgb(32, 32, 32, 0.8)")
            .set("padding-left", "10px")
            .set("padding-bottom", "10px");
        header = new H3("Cookie Consent");
        text = new Paragraph(
            "By continuing to browse or by clicking 'Accept', you agree to the storing of cookies on your\ndevice to enhance your site experience."
        );

        termsButton = new Button("Terms", event -> {
            getUI().ifPresent(ui-> {
                ui.navigate(Terms.class);
            });
        });
        termsButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        termsButton.getElement().getStyle()
            .set("float", "right")
            .set("margin-right", "20px");

        acceptButton = new Button("ACCEPT AND CLOSE");
        acceptButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        acceptButton.addClickListener(event -> {
            CookieControl.createCookie("AskedConsent", "true", 365 * 2 * 60 * 60); //lasts for a year
            this.getElement().getParent().removeChild(this.getElement());
        });

        add(header, termsButton, text, acceptButton);
    }
}
